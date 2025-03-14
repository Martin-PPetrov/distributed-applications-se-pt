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
@Table(name = "library")
@Getter
@Setter
@NoArgsConstructor
public class Library extends BaseEntity {

    @Column(nullable = false)
    @Size(max = 30)
    private String name;

    @Column
    @Size(max = 50)
    private String address;

    @Column
    @Size(max = 30)
    private String city;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "open_since")
    private Date openSince;

    @Column(name = "max_capacity_of_people")
    private Integer maxCapacityOfPeople;

    @ManyToMany
    @JoinTable(name = "library_book",
            joinColumns = @JoinColumn(name = "library_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books = new ArrayList<>();

    public Library(String name) {
        this.name = name;
    }

}
