package by.diploma.gym.service;

import by.diploma.gym.dto.request.GymInfoRequest;
import by.diploma.gym.dto.response.GymInfoResponse;

import java.util.List;
import java.util.UUID;

public interface GymInfoService {

    GymInfoResponse create(GymInfoRequest request);
    GymInfoResponse update(UUID id, GymInfoRequest request);
    void delete(UUID id);
    GymInfoResponse getById(UUID id);
    GymInfoResponse getByAddress(String address);
    List<GymInfoResponse> getAll();

}
