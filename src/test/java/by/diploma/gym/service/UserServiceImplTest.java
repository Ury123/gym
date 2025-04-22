package by.diploma.gym.service;

import by.diploma.gym.dto.request.user.UserRegisterRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.user.UserListResponse;
import by.diploma.gym.dto.response.user.UserResponse;
import by.diploma.gym.exceptions.EntityNotFoundException;
import by.diploma.gym.mapper.UserMapper;
import by.diploma.gym.model.User;
import by.diploma.gym.model.UserRole;
import by.diploma.gym.repository.UserRepository;
import by.diploma.gym.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private User entity;

    @Mock
    private UserResponse response;

    private final UUID id = UUID.randomUUID();
    private final String registerEmail = "test@example.com";
    private final String registerPhone = "+375291234567";
    private final String registerFirstName = "John";
    private final String registerLastName = "Doe";
    private final LocalDate registerDateOfBirth = LocalDate.of(1990, 1, 1);
    private final String registerPassword = "password123";
    private final String updateFirstName = "Jane";
    private final String updateLastName = "Smith";
    private final String updatePhoneNumber = "+375291111111";
    private final LocalDate updateDateOfBirth = LocalDate.of(1990, 2, 2);

    private final UserRegisterRequest registerRequest = new UserRegisterRequest();
    private final UserUpdateRequest updateRequest = new UserUpdateRequest();

    @BeforeEach
    void setUp() {
        registerRequest.setFirstName(registerFirstName);
        registerRequest.setLastName(registerLastName);
        registerRequest.setEmail(registerEmail);
        registerRequest.setPhoneNumber(registerPhone);
        registerRequest.setPassword(registerPassword);
        registerRequest.setDateOfBirth(registerDateOfBirth);

        updateRequest.setFirstName(updateFirstName);
        updateRequest.setLastName(updateLastName);
        updateRequest.setPhoneNumber(updatePhoneNumber);
        updateRequest.setDateOfBirth(updateDateOfBirth);
    }

    @Test
    void test_register_shouldSaveAndReturnResponse() {
        when(userMapper.toEntity(registerRequest)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getEmail()).thenReturn(registerEmail);

        UserResponse result = userService.register(registerRequest);

        assertEquals(registerEmail, result.getEmail());
        verify(userRepository).save(entity);
    }

    @Test
    void test_update_shouldUpdateEntityAndReturnResponse() {
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(userMapper).updateEntityFromRequest(updateRequest, entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getId()).thenReturn(id);

        UserResponse result = userService.update(id, updateRequest);

        assertEquals(id, result.getId());
    }

    @Test
    void test_getById_shouldReturnResponseIfFound() {
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getId()).thenReturn(id);

        UserResponse result = userService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void test_getByEmail_shouldReturnResponseIfFound() {
        when(userRepository.findByEmail(registerEmail)).thenReturn(Optional.of(entity));
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getEmail()).thenReturn(registerEmail);

        UserResponse result = userService.getByEmail(registerEmail);

        assertEquals(registerEmail, result.getEmail());
    }

    @Test
    void test_getByPhone_shouldReturnResponseIfFound() {
        when(userRepository.findByPhoneNumber(registerPhone)).thenReturn(Optional.of(entity));
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getPhoneNumber()).thenReturn(registerPhone);

        UserResponse result = userService.getByPhoneNumber(registerPhone);

        assertEquals(registerPhone, result.getPhoneNumber());
    }

    @Test
    void test_getAll_shouldReturnUserListResponse() {
        List<User> entities = List.of(entity);
        List<UserResponse> responses = List.of(response);

        when(userRepository.findAll()).thenReturn(entities);
        when(userMapper.toResponseList(entities)).thenReturn(responses);
        when(response.getId()).thenReturn(id);

        UserListResponse result = userService.getAll();

        assertEquals(1, result.getUsers().size());
        assertEquals(id, result.getUsers().get(0).getId());
    }

    @Test
    void test_delete_shouldDeleteUserIfExists() {
        when(userRepository.existsById(id)).thenReturn(true);

        userService.delete(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void test_getById_shouldThrowIfNotFound() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getById(id));
    }

}
