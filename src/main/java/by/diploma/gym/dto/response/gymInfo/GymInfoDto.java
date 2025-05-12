package by.diploma.gym.dto.response.gymInfo;

import lombok.Data;

import java.util.UUID;

@Data
public class GymInfoDto {

    private UUID id;
    private String address;
    private String phoneNumber;
    private String description;

}
