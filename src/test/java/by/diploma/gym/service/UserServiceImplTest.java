package by.diploma.gym.service;

import by.diploma.gym.dto.request.user.UserRegistrationRequest;
import by.diploma.gym.dto.request.user.UserSearchRequest;
import by.diploma.gym.dto.request.user.UserUpdateRequest;
import by.diploma.gym.dto.response.PageResponse;
import by.diploma.gym.dto.response.user.UserDto;
import by.diploma.gym.exceptions.EntityNotFoundException;
import by.diploma.gym.mapper.UserMapper;
import by.diploma.gym.model.User;
import by.diploma.gym.repository.UserRepository;
import by.diploma.gym.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
    private UserDto response;

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

    private final UserRegistrationRequest registerRequest = new UserRegistrationRequest();
    private final UserUpdateRequest updateRequest = new UserUpdateRequest();
    private final UserSearchRequest searchRequest = new UserSearchRequest();

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

        UserDto result = userService.register(registerRequest);

        assertEquals(registerEmail, result.getEmail());
        verify(userRepository).save(entity);
    }

    @Test
    void test_update_shouldUpdateEntityAndReturnResponse() {
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userMapper.updateEntityFromRequest(updateRequest, entity)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getId()).thenReturn(id);

        UserDto result = userService.update(id, updateRequest);

        assertEquals(id, result.getId());
    }

    @Test
    void test_getById_shouldReturnResponseIfFound() {
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userMapper.toResponse(entity)).thenReturn(response);
        when(response.getId()).thenReturn(id);

        UserDto result = userService.getById(id);

        assertEquals(id, result.getId());
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

    @Test
    void test_search_shouldReturnPageResponse() {
        searchRequest.setEmail("example");
        searchRequest.setPage(0);
        searchRequest.setSize(10);
        searchRequest.setSortBy("lastName");
        searchRequest.setSortDirection("asc");

        Pageable expectedPageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "lastName"));

        List<User> users = List.of(entity);
        Page<User> page = new PageImpl<>(users, expectedPageable, users.size());

        PageResponse<UserDto> expectedResponse = new PageResponse<>();
        expectedResponse.setContent(List.of(response));
        expectedResponse.setPage(0);
        expectedResponse.setSize(10);
        expectedResponse.setTotalElements(1L);
        expectedResponse.setTotalPages(1);

        when(userRepository.findAll(any(Specification.class), eq(expectedPageable))).thenReturn(page);
        when(userMapper.toPageResponse(page)).thenReturn(expectedResponse);
        when(response.getEmail()).thenReturn(registerEmail);

        PageResponse<UserDto> result = userService.search(searchRequest);

        assertEquals(1, result.getContent().size());
        assertEquals(registerEmail, result.getContent().get(0).getEmail());
        assertEquals(1, result.getTotalElements());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());

        verify(userRepository).findAll(any(Specification.class), eq(expectedPageable));
        verify(userMapper).toPageResponse(page);
    }

    @Test
    void test_search_shouldReturnEmptyPageResponse() {
        searchRequest.setPage(0);
        searchRequest.setSize(10);
        searchRequest.setSortBy("lastName");
        searchRequest.setSortDirection("asc");

        Pageable expectedPageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "lastName"));
        Page<User> emptyPage = new PageImpl<>(List.of(), expectedPageable, 0);

        PageResponse<UserDto> expectedResponse = new PageResponse<>();
        expectedResponse.setContent(List.of());
        expectedResponse.setPage(0);
        expectedResponse.setSize(10);
        expectedResponse.setTotalElements(0L);
        expectedResponse.setTotalPages(0);

        when(userRepository.findAll(any(Specification.class), eq(expectedPageable))).thenReturn(emptyPage);
        when(userMapper.toPageResponse(emptyPage)).thenReturn(expectedResponse);

        PageResponse<UserDto> result = userService.search(searchRequest);

        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());

        verify(userRepository).findAll(any(Specification.class), eq(expectedPageable));
        verify(userMapper).toPageResponse(emptyPage);
    }

}
