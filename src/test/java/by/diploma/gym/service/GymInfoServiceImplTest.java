package by.diploma.gym.service;

import by.diploma.gym.dto.request.gymInfo.GymInfoRequest;
import by.diploma.gym.dto.response.gymInfo.GymInfoResponse;
import by.diploma.gym.dto.response.gymInfo.GymListResponse;
import by.diploma.gym.mapper.GymInfoMapper;
import by.diploma.gym.model.GymInfo;
import by.diploma.gym.repository.GymInfoRepository;
import by.diploma.gym.service.Impl.GymInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GymInfoServiceImplTest {

    @InjectMocks
    private GymInfoServiceImpl gymInfoService;

    @Mock
    private GymInfoRepository gymInfoRepository;

    @Mock
    private GymInfoMapper gymInfoMapper;

    @Mock
    private GymInfo entity;

    @Mock
    private GymInfoResponse response;

    private final UUID id = UUID.randomUUID();
    private final String address = "Main St";

    private final GymInfoRequest request = new GymInfoRequest();

    @BeforeEach
    void setUp() {
        request.setAddress(address);
        request.setPhoneNumber("123 456789");
        request.setDescription("Nice gym");

    }

    @Test
    void test_create_shouldSaveAndReturnResponse() {
        when(gymInfoMapper.toEntity(request)).thenReturn(entity);
        when(gymInfoRepository.save(entity)).thenReturn(entity);
        when(gymInfoMapper.toResponse(entity)).thenReturn(response);

        when(response.getAddress()).thenReturn(address);

        GymInfoResponse result = gymInfoService.create(request);
        assertEquals("Main St", result.getAddress());
        verify(gymInfoRepository).save(entity);
    }

    @Test
    void test_getById_shouldReturnResponseIfFound() {
        when(response.getId()).thenReturn(id);

        when(gymInfoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(gymInfoMapper.toResponse(entity)).thenReturn(response);

        GymInfoResponse result = gymInfoService.getById(id);
        assertEquals(id, result.getId());
    }

    @Test
    void test_getById_shouldThrowExceptionIfNotFound() {
        when(gymInfoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> gymInfoService.getById(id));
    }

    @Test
    void test_getByAddress_shouldReturnResponseIfFound() {
        when(gymInfoRepository.findByAddress(address)).thenReturn(Optional.of(entity));
        when(gymInfoMapper.toResponse(entity)).thenReturn(response);

        when(response.getId()).thenReturn(id);
        when(response.getAddress()).thenReturn(address);

        GymInfoResponse result = gymInfoService.getByAddress(address);

        assertEquals(id, result.getId());
        assertEquals(address, result.getAddress());
    }

    @Test
    void test_getByAddress_shouldThrowExceptionIfNotFound() {
        when(gymInfoRepository.findByAddress(address)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gymInfoService.getByAddress(address));
    }

    @Test
    void test_getAll_shouldReturnGymListResponse() {
        List<GymInfo> entities = List.of(entity);
        List<GymInfoResponse> responses = List.of(response);

        when(response.getId()).thenReturn(id);
        when(response.getAddress()).thenReturn(address);

        when(gymInfoRepository.findAll()).thenReturn(entities);
        when(gymInfoMapper.toResponseList(entities)).thenReturn(responses);

        GymListResponse result = gymInfoService.getAll();

        assertEquals(1, result.getGyms().size());
        assertEquals(id, result.getGyms().get(0).getId());
        assertEquals(address, result.getGyms().get(0).getAddress());
    }

    @Test
    void test_delete_shouldRemoveEntityIfExists() {
        when(gymInfoRepository.existsById(id)).thenReturn(true);
        gymInfoService.delete(id);
        verify(gymInfoRepository).deleteById(id);
    }

}
