package by.diploma.gym.service.Impl;

import by.diploma.gym.dto.request.user.UserRegistrationRequest;
import by.diploma.gym.dto.request.user.UserSearchRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.user.UserListResponse;
import by.diploma.gym.dto.response.user.UserDto;
import by.diploma.gym.exceptions.EntityNotFoundException;
import by.diploma.gym.mapper.UserMapper;
import by.diploma.gym.model.User;
import by.diploma.gym.repository.UserRepository;
import by.diploma.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_WITH_ID_ERR_MSG = "User not found with id: ";
    private static final String USER_NOT_FOUND_WITH_EMAIL_ERR_MSG = "User not found with email: ";
    private static final String USER_NOT_FOUND_WITH_PHONE_ERR_MSG = "User not found with phone number: ";
    private static final String USER_NOT_FOUND_WITH_NAME_ERR_MSG = "User not found with name: ";

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
    public UserDto getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_EMAIL_ERR_MSG + email));
    }

    @Override
    public UserDto getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_PHONE_ERR_MSG + phoneNumber));
    }

    @Override
    public UserDto getByFullName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_NAME_ERR_MSG + firstName + " " + lastName));
    }

    @Override
    public UserListResponse getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> responses = userMapper.toResponseList(users);
        UserListResponse result = new UserListResponse();
        result.setUsers(responses);
        return result;
    }

    @Override
    public List<UserDto> search(UserSearchRequest request) {
        Specification<User> spec = Specification.where(null);

        if (request.getEmail() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
        }

        if (request.getPhoneNumber() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("phoneNumber")), "%" + request.getPhoneNumber().toLowerCase() + "%"));
        }

        if (request.getFirstName() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("firstName")), "%" + request.getFirstName().toLowerCase() + "%"));
        }

        if (request.getLastName() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("lastName")), "%" + request.getLastName().toLowerCase() + "%"));
        }

        List<User> users = userRepository.findAll(spec);
        return userMapper.toResponseList(users);
    }


}
