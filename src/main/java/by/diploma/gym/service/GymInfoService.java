package by.diploma.gym.service;

import by.diploma.gym.dto.request.gymInfo.GymInfoRequest;
import by.diploma.gym.dto.response.gymInfo.GymInfoDto;
import by.diploma.gym.dto.response.gymInfo.GymListResponse;

import java.util.UUID;

public interface GymInfoService {

    GymInfoDto create(GymInfoRequest request);
    GymInfoDto update(UUID id, GymInfoRequest request);
    void delete(UUID id);
    GymInfoDto getById(UUID id);
    GymInfoDto getByAddress(String address);
    GymListResponse getAll();

}
