package by.diploma.gym.mapper;

import by.diploma.gym.dto.request.gymInfo.GymInfoRequest;
import by.diploma.gym.dto.response.gymInfo.GymInfoDto;
import by.diploma.gym.model.GymInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface GymInfoMapper {

    GymInfo toEntity(GymInfoRequest request);

    GymInfoDto toResponse(GymInfo entity);

    GymInfo updateEntityFromRequest(GymInfoRequest request, @MappingTarget GymInfo entity);

    List<GymInfoDto> toResponseList(List<GymInfo> entities);

}
