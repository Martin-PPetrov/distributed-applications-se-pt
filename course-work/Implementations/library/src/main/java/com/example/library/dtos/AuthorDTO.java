package com.example.library.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AuthorDTO {

    private Long id;

    @NotEmpty
    @Size(max = 30)
    private String firstName;

    @NotEmpty
    @Size(max = 30)
    private String lastName;

    @Size(max = 30)
    private String nationality;

    private Date dateOfBirth;
    private Boolean isActive;
    private Boolean isPopular;
    private List<String> books = new ArrayList<>();

}
