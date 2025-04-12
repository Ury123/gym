package by.diploma.gym.service;

import by.diploma.gym.dto.request.GymInfoRequest;
import by.diploma.gym.dto.response.GymInfoResponse;
import by.diploma.gym.mapper.GymInfoMapper;
import by.diploma.gym.model.GymInfo;
import by.diploma.gym.repository.GymInfoRepository;
import by.diploma.gym.service.Impl.GymInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GymInfoServiceImplTest {

    @InjectMocks
    private GymInfoServiceImpl gymInfoService;

    @Mock
    private GymInfoRepository gymInfoRepository;

    @Mock
    private GymInfoMapper gymInfoMapper;

    private final UUID id = UUID.randomUUID();
    private final String address = "Main St";

    private final GymInfoRequest request = new GymInfoRequest();
    private final GymInfo entity = new GymInfo();
    private final GymInfoResponse response = new GymInfoResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request.setAddress(address);
        request.setPhoneNumber("123 456789");
        request.setDescription("Nice gym");

        entity.setId(id);
        entity.setAddress(address);

        response.setId(id);
        response.setAddress(address);
    }

    @Test
    void testCreate() {
        when(gymInfoMapper.toEntity(request)).thenReturn(entity);
        when(gymInfoRepository.save(entity)).thenReturn(entity);
        when(gymInfoMapper.toResponse(entity)).thenReturn(response);

        GymInfoResponse result = gymInfoService.create(request);
        assertEquals("Main St", result.getAddress());
        verify(gymInfoRepository).save(entity);
    }

    @Test
    void testGetById_Found() {
        when(gymInfoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(gymInfoMapper.toResponse(entity)).thenReturn(response);

        GymInfoResponse result = gymInfoService.getById(id);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetById_NotFound() {
        when(gymInfoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> gymInfoService.getById(id));
    }

    @Test
    void testGetByAddress_Found() {
        when(gymInfoRepository.findByAddress(address)).thenReturn(Optional.of(entity));
        when(gymInfoMapper.toResponse(entity)).thenReturn(response);

        GymInfoResponse result = gymInfoService.getByAddress(address);

        assertEquals(id, result.getId());
        assertEquals(address, result.getAddress());
    }

    @Test
    void testGetByAddress_NotFound() {
        when(gymInfoRepository.findByAddress(address)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gymInfoService.getByAddress(address));
    }

    @Test
    void testGetAll() {
        when(gymInfoRepository.findAll()).thenReturn(List.of(entity));
        when(gymInfoMapper.toResponse(entity)).thenReturn(response);

        List<GymInfoResponse> result = gymInfoService.getAll();

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
        assertEquals(address, result.get(0).getAddress());
    }

    @Test
    void testDelete() {
        when(gymInfoRepository.existsById(id)).thenReturn(true);
        gymInfoService.delete(id);
        verify(gymInfoRepository).deleteById(id);
    }

}
