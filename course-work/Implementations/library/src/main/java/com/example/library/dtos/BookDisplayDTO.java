package com.example.library.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class BookDisplayDTO {

    private String title;
    private String author;
    private String genre;
    private int pagesCount;
    private Date publishedOn;
    private Boolean isPopular;

}
