package by.diploma.gym.dto.request.user;

import lombok.Data;

@Data
public class UserSearchRequest {

    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "lastName";
    private String sortDirection = "asc";

}
