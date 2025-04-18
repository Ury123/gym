package by.diploma.gym.service.Impl;

import by.diploma.gym.dto.request.user.UserRequest;
import by.diploma.gym.dto.response.user.UserListResponse;
import by.diploma.gym.dto.response.user.UserResponse;
import by.diploma.gym.exceptions.EntityNotFoundException;
import by.diploma.gym.mapper.UserMapper;
import by.diploma.gym.model.User;
import by.diploma.gym.repository.UserRepository;
import by.diploma.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_WITH_ID = "User not found with id: ";
    private static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with email: ";
    private static final String USER_NOT_FOUND_WITH_PHONE = "User not found with phone number: ";
    private static final String USER_NOT_FOUND_WITH_NAME = "User not found with name: ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse update(UUID id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + id));

        userMapper.updateEntityFromRequest(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + id));
    }

    @Override
    public UserResponse getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));
    }

    @Override
    public UserResponse getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_PHONE + phoneNumber));
    }

    @Override
    public UserResponse getByFullName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_NAME + firstName + " " + lastName));
    }

    @Override
    public UserListResponse getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = userMapper.toResponseList(users);
        UserListResponse listResponse = new UserListResponse();
        listResponse.setUsers(userResponses);
        return listResponse;
    }

}
