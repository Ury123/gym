package by.diploma.gym.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class GymInfoResponse {

    private UUID id;
    private String address;
    private String phoneNumber;
    private String description;

}
