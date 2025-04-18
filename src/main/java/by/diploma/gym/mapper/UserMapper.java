package by.diploma.gym.mapper;

import by.diploma.gym.dto.request.user.UserRequest;
import by.diploma.gym.dto.response.user.UserResponse;
import by.diploma.gym.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest request);

    UserResponse toResponse(User user);

    User updateEntityFromRequest(UserRequest request, @MappingTarget User user);

    List<UserResponse> toResponseList(List<User> users);

}
