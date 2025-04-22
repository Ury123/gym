package by.diploma.gym.service.Impl;

import by.diploma.gym.dto.request.user.UserRegisterRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
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

    private static final String USER_NOT_FOUND_WITH_ID_ERR_MSG = "User not found with id: ";
    private static final String USER_NOT_FOUND_WITH_EMAIL_ERR_MSG = "User not found with email: ";
    private static final String USER_NOT_FOUND_WITH_PHONE_ERR_MSG = "User not found with phone number: ";
    private static final String USER_NOT_FOUND_WITH_NAME_ERR_MSG = "User not found with name: ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse register(UserRegisterRequest request) {
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse update(UUID id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID_ERR_MSG + id));

        userMapper.updateEntityFromRequest(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_WITH_ID_ERR_MSG + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID_ERR_MSG + id));
    }

    @Override
    public UserResponse getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_EMAIL_ERR_MSG + email));
    }

    @Override
    public UserResponse getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_PHONE_ERR_MSG + phoneNumber));
    }

    @Override
    public UserResponse getByFullName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_NAME_ERR_MSG + firstName + " " + lastName));
    }

    @Override
    public UserListResponse getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = userMapper.toResponseList(users);
        UserListResponse result = new UserListResponse();
        result.setUsers(responses);
        return result;
    }

}
