package by.diploma.gym.dto.request;

import lombok.Data;

@Data
public class PageableRequest {

    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id";
    private String sortDirection = "asc";

}
