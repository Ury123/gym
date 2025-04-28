package by.diploma.gym.dto.response.gymInfo;

import lombok.Data;

import java.util.List;

@Data
public class GymListResponse  {
    List<GymInfoDto> gyms;
}
