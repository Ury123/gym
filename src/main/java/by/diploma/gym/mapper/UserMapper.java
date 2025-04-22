package by.diploma.gym.mapper;

import by.diploma.gym.dto.request.user.UserRegisterRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.user.UserResponse;
import by.diploma.gym.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    User toEntity(UserRegisterRequest request);

    UserResponse toResponse(User user);

    void updateEntityFromRequest(UserUpdateRequest request, @MappingTarget User user);

    List<UserResponse> toResponseList(List<User> users);

}
