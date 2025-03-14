package com.example.library.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class LibraryDTO {

    private Long id;

    @NotEmpty
    @Size(max = 30)
    private String name;

    @Size(max = 50)
    private String address;

    @Size(max = 30)
    private String city;

    private Boolean isOpen;
    private Date openSince;
    private Integer maxCapacityOfPeople;
    private List<String> books = new ArrayList<>();

}
