package by.diploma.gym.service.Impl;

import by.diploma.gym.dto.request.gymInfo.GymInfoRequest;
import by.diploma.gym.dto.response.gymInfo.GymInfoResponse;
import by.diploma.gym.dto.response.gymInfo.GymListResponse;
import by.diploma.gym.exceptions.EntityNotFoundException;
import by.diploma.gym.mapper.GymInfoMapper;
import by.diploma.gym.model.GymInfo;
import by.diploma.gym.repository.GymInfoRepository;
import by.diploma.gym.service.GymInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GymInfoServiceImpl implements GymInfoService {

    private static final String GYM_NOT_FOUND_WITH_ID = "Gym not found with id: ";
    private static final String GYM_NOT_FOUND_WITH_ADDRESS = "Gym not found with address: ";


    private final GymInfoRepository gymInfoRepository;
    private final GymInfoMapper gymInfoMapper;

    @Override
    public GymInfoResponse create(GymInfoRequest request) {
        GymInfo entity = gymInfoMapper.toEntity(request);
        GymInfo saved = gymInfoRepository.save(entity);
        return gymInfoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public GymInfoResponse update(UUID id, GymInfoRequest request) {
        GymInfo entity = gymInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GYM_NOT_FOUND_WITH_ID + id));

        gymInfoMapper.updateEntityFromRequest(request, entity);
        GymInfo updated = gymInfoRepository.save(entity);
        return gymInfoMapper.toResponse(updated);
    }

    @Override
    public void delete(UUID id) {
        if (!gymInfoRepository.existsById(id)) {
            throw new EntityNotFoundException(GYM_NOT_FOUND_WITH_ID + id);
        }
        gymInfoRepository.deleteById(id);
    }

    @Override
    public GymInfoResponse getById(UUID id) {
        GymInfo gymInfo = gymInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GYM_NOT_FOUND_WITH_ID + id));
        return gymInfoMapper.toResponse(gymInfo);
    }

    @Override
    public GymInfoResponse getByAddress(String address) {
        return gymInfoRepository.findByAddress(address)
                .map(gymInfoMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(GYM_NOT_FOUND_WITH_ADDRESS + address));
    }

    @Override
    public GymListResponse getAll() {
        List<GymInfoResponse> gyms = gymInfoMapper.toResponseList(gymInfoRepository.findAll());

        GymListResponse response = new GymListResponse();
        response.setGyms(gyms);
        return response;
    }

}
