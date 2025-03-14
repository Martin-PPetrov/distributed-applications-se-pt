package com.example.library.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookDTO {

    private Long id;

    @NotEmpty
    @Size(max = 70)
    private String title;

    @Size(max = 30)
    private String genre;

    private int pagesCount;
    private Date publishedOn;
    private Boolean isPopular;
    private String author;
    private List<String> libraries = new ArrayList<>();

}
