package by.diploma.gym.dto.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    @Size(min = 6, max = 255)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$")
    private String email;

    @Size(min = 9, max = 13)
    @Pattern(regexp = "^(\\+375)?(44|29|25|33)[0-9]{7}$")
    private String phoneNumber;

    @Past
    private LocalDate dateOfBirth;

}
