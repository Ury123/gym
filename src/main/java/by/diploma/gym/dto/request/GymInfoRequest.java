package by.diploma.gym.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GymInfoRequest {

    @NotNull
    @Size(max = 50)
    private String address;

    @NotNull
    @Size(min = 6, max = 10)
    @Pattern(regexp = "^\\d{2,4}\\s?[0-9]{6}$")
    private String phoneNumber;

    @NotNull
    private String description;

}
