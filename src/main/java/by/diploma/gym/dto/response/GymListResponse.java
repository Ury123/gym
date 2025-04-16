package by.diploma.gym.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GymListResponse  {
    List<GymInfoResponse> gyms;
}
