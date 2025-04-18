package by.diploma.gym.dto.request.user;

import by.diploma.gym.model.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {

    @NotNull
    private UserRole userRole;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Size(min = 6, max = 255)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String email;

    @NotNull
    @Size(min = 9, max = 13)
    @Pattern(regexp = "^(\\+375)?(44|29|25|33)[0-9]{7}$")
    private String phoneNumber;

    @Past
    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Size(min = 8, max = 255)
    private String password;

}
