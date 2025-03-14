package com.example.library.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
public class Author extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    @Size(max = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 30)
    private String lastName;

    @Column
    @Size(max = 30)
    private String nationality;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_popular")
    private Boolean isPopular;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
