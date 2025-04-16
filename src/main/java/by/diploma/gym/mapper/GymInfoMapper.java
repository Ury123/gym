package by.diploma.gym.mapper;

import by.diploma.gym.dto.request.GymInfoRequest;
import by.diploma.gym.dto.response.GymInfoResponse;
import by.diploma.gym.model.GymInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GymInfoMapper {

    GymInfo toEntity(GymInfoRequest request);

    GymInfoResponse toResponse(GymInfo entity);

    GymInfo updateEntityFromRequest(GymInfoRequest request, @MappingTarget GymInfo entity);

    List<GymInfoResponse> toResponseList(List<GymInfo> entities);

}
