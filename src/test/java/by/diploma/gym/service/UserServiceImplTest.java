package by.diploma.gym.service;

import by.diploma.gym.dto.request.user.UserRequest;
import by.diploma.gym.dto.response.user.UserListResponse;
import by.diploma.gym.dto.response.user.UserResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private final String email = "test@example.com";
    private final String phone = "+375291234567";
    private final String firstName = "John";
    private final String lastName = "Doe";
    private final LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
    private final String password = "password123";

    private final UserRequest request = new UserRequest();

    @BeforeEach
    void setUp() {
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setPhoneNumber(phone);
        request.setDateOfBirth(dateOfBirth);
        request.setPassword(password);
        request.setUserRole(UserRole.USER);
    }

    @Test
    void test_create_shouldSaveAndReturnResponse() {
        when(userMapper.toEntity(request)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getEmail()).thenReturn(email);

        UserResponse result = userService.create(request);

        assertEquals(email, result.getEmail());
        verify(userRepository).save(entity);
    }

    @Test
    void test_getById_shouldReturnResponseIfFound() {
        when(response.getId()).thenReturn(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userMapper.toResponse(entity)).thenReturn(response);

        UserResponse result = userService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void test_getById_shouldThrowExceptionIfNotFound() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getById(id));
    }

    @Test
    void test_getByEmail_shouldReturnResponseIfFound() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(entity));
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getEmail()).thenReturn(email);

        UserResponse result = userService.getByEmail(email);

        assertEquals(email, result.getEmail());
    }

    @Test
    void test_getByEmail_shouldThrowExceptionIfNotFound() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getByEmail(email));
    }

    @Test
    void test_getByPhone_shouldReturnResponseIfFound() {
        when(userRepository.findByPhoneNumber(phone)).thenReturn(Optional.of(entity));
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getPhoneNumber()).thenReturn(phone);

        UserResponse result = userService.getByPhoneNumber(phone);

        assertEquals(phone, result.getPhoneNumber());
    }

    @Test
    void test_getByPhone_shouldThrowExceptionIfNotFound() {
        when(userRepository.findByPhoneNumber(phone)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getByPhoneNumber(phone));
    }

    @Test
    void test_getAll_shouldReturnUserListResponse() {
        List<User> entities = List.of(entity);
        List<UserResponse> responses = List.of(response);

        when(response.getId()).thenReturn(id);
        when(response.getEmail()).thenReturn(email);

        when(userRepository.findAll()).thenReturn(entities);
        when(userMapper.toResponseList(entities)).thenReturn(responses);

        UserListResponse result = userService.getAll();

        assertEquals(1, result.getUsers().size());
        assertEquals(id, result.getUsers().get(0).getId());
        assertEquals(email, result.getUsers().get(0).getEmail());
    }

    @Test
    void test_delete_shouldRemoveUserIfExists() {
        when(userRepository.existsById(id)).thenReturn(true);

        userService.delete(id);

        verify(userRepository).deleteById(id);
    }

}
