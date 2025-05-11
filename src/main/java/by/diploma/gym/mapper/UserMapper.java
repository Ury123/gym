package by.diploma.gym.mapper;

import by.diploma.gym.dto.request.user.UserRegistrationRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.PageResponse;
import by.diploma.gym.dto.response.user.UserDto;
import by.diploma.gym.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    User toEntity(UserRegistrationRequest request);

    UserDto toResponse(User user);

    User updateEntityFromRequest(UserUpdateRequest request, @MappingTarget User user);

    List<UserDto> toResponseList(List<User> users);

    @Mapping(target = "page", expression = "java(page.getNumber())")
    @Mapping(target = "size", expression = "java(page.getSize())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    @Mapping(target = "totalElements", expression = "java(page.getTotalElements())")
    @Mapping(target = "content", expression = "java(toResponseList(page.getContent()))")
    PageResponse<UserDto> toPageResponse(Page<User> page);
}
