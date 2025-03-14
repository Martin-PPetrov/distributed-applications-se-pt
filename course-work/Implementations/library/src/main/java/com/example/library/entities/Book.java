package com.example.library.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book extends BaseEntity {

    @Column(nullable = false)
    @Size(max = 70)
    private String title;

    @Column
    @Size(max = 30)
    private String genre;

    @Column(name = "pages_count")
    private int pagesCount;

    @Column(name = "published_on")
    private Date publishedOn;

    @Column(name = "is_popular")
    private Boolean isPopular;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(mappedBy = "books")
    private List<Library> libraries = new ArrayList<>();

    public Book(String title) {
        this.title = title;
    }
}
