package by.diploma.gym.service;

import by.diploma.gym.dto.request.gymInfo.GymInfoRequest;
import by.diploma.gym.dto.response.gymInfo.GymInfoResponse;
import by.diploma.gym.dto.response.gymInfo.GymListResponse;

import java.util.UUID;

public interface GymInfoService {

    GymInfoResponse create(GymInfoRequest request);
    GymInfoResponse update(UUID id, GymInfoRequest request);
    void delete(UUID id);
    GymInfoResponse getById(UUID id);
    GymInfoResponse getByAddress(String address);
    GymListResponse getAll();

}
