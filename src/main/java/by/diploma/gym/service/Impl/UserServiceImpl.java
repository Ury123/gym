package by.diploma.gym.service.Impl;

import by.diploma.gym.dto.request.user.UserRegistrationRequest;
import by.diploma.gym.dto.request.user.UserSearchRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.PageResponse;
import by.diploma.gym.dto.response.user.UserDto;
import by.diploma.gym.exceptions.EntityNotFoundException;
import by.diploma.gym.mapper.UserMapper;
import by.diploma.gym.model.User;
import by.diploma.gym.repository.UserRepository;
import by.diploma.gym.service.UserService;
import by.diploma.gym.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_WITH_ID_ERR_MSG = "User not found with id: ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto register(UserRegistrationRequest request) {
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserDto update(UUID id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID_ERR_MSG + id));

        User updated = userMapper.updateEntityFromRequest(request, user);
        return userMapper.toResponse(userRepository.save(updated));
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_WITH_ID_ERR_MSG + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID_ERR_MSG + id));
    }

    @Override
    public PageResponse<UserDto> getAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);

        PageResponse<UserDto> response = new PageResponse<>();
        response.setContent(userMapper.toResponseList(page.getContent()));
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());

        return response;
    }

    @Override
    public PageResponse<UserDto> search(UserSearchRequest request, Pageable pageable) {
        Specification<User> spec = Specification
                .where(UserSpecification.emailContains(request.getEmail()))
                .and(UserSpecification.phoneContains(request.getPhoneNumber()))
                .and(UserSpecification.firstNameContains(request.getFirstName()))
                .and(UserSpecification.lastNameContains(request.getLastName()));

        Page<User> page = userRepository.findAll(spec, pageable);

        PageResponse<UserDto> response = new PageResponse<>();
        response.setContent(userMapper.toResponseList(page.getContent()));
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());

        return response;
    }

}
