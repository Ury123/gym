package by.diploma.gym.service;

import by.diploma.gym.dto.request.user.UserRegistrationRequest;
import by.diploma.gym.dto.request.user.UserSearchRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.PageResponse;
import by.diploma.gym.dto.response.user.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserDto register(UserRegistrationRequest request);

    UserDto update(UUID id, UserUpdateRequest request);

    void delete(UUID id);

    UserDto getById(UUID id);

    PageResponse<UserDto> getAll(Pageable pageable);

    PageResponse<UserDto> search(UserSearchRequest request, Pageable pageable);

}
