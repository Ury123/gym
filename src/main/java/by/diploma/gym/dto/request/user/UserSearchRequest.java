package by.diploma.gym.dto.request.user;

import lombok.Data;

@Data
public class UserSearchRequest {

    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;

}
