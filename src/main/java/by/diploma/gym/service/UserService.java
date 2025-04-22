package by.diploma.gym.service;

import by.diploma.gym.dto.request.user.UserRegisterRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.user.UserListResponse;
import by.diploma.gym.dto.response.user.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse register(UserRegisterRequest request);

    UserResponse update(UUID id, UserUpdateRequest request);

    void delete(UUID id);

    UserResponse getById(UUID id);

    UserResponse getByEmail(String email);

    UserResponse getByPhoneNumber(String phoneNumber);

    UserResponse getByFullName(String firstName, String lastName);

    UserListResponse getAll();

}
