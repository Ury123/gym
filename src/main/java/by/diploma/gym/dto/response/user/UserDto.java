package by.diploma.gym.dto.response.user;

import by.diploma.gym.model.UserRole;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

}
