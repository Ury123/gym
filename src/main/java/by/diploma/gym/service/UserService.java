package by.diploma.gym.service;

import by.diploma.gym.dto.request.user.UserRegistrationRequest;
import by.diploma.gym.dto.request.user.UserSearchRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.user.UserListResponse;
import by.diploma.gym.dto.response.user.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto register(UserRegistrationRequest request);

    UserDto update(UUID id, UserUpdateRequest request);

    void delete(UUID id);

    UserDto getById(UUID id);

    UserDto getByEmail(String email);

    UserDto getByPhoneNumber(String phoneNumber);

    UserDto getByFullName(String firstName, String lastName);

    UserListResponse getAll();

    List<UserDto> search(UserSearchRequest request);

}
