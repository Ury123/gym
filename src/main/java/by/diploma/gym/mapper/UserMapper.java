package by.diploma.gym.mapper;

import by.diploma.gym.dto.request.user.UserRegistrationRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.user.UserDto;
import by.diploma.gym.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    User toEntity(UserRegistrationRequest request);

    UserDto toResponse(User user);

    User updateEntityFromRequest(UserUpdateRequest request, @MappingTarget User user);

    List<UserDto> toResponseList(List<User> users);

}
