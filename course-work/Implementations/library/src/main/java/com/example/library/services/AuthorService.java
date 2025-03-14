package com.example.library.services;

import com.example.library.dtos.AuthorDTO;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;

    private final BookService bookService;

    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper, BookService bookService) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    public Author getOrCreateAuthor(String name) {
//        if (name == null) {
//            Author author = new Author();
//            authorRepository.save(author);
//            return author;
//        }

        String[] nameArray = name.split("\\s+");

        String firstName = nameArray[0];
        String lastName;

        if (nameArray.length > 1) {
            lastName = nameArray[1];
        } else {
            lastName = "";
        }

        Optional<Author> optionalAuthor = authorRepository.findByFirstNameAndLastName(firstName, lastName);

        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            Author author = new Author(firstName, lastName);
            authorRepository.save(author);
            return author;
        }
    }

    public AuthorDTO getAuthorByName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(this::entityToDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void createAuthor(AuthorDTO authorDTO) {
        Author author = dtoToEntity(authorDTO);
        authorRepository.save(author);
    }

    private AuthorDTO entityToDto(Author author) {
        AuthorDTO authorDTO = modelMapper.map(author, AuthorDTO.class);

        List<String> books = author.getBooks().stream().map(Book::getTitle).toList();
        authorDTO.setBooks(books);

        return authorDTO;
    }

    private Author dtoToEntity(AuthorDTO authorDTO) {
        Author author = modelMapper.map(authorDTO, Author.class);

        List<Book> books = new ArrayList<>();

        for (String bookTitle : authorDTO.getBooks()) {
            Book book = bookService.getOrCreateBook(bookTitle);
            books.add(book);
        }

        author.setBooks(books);

        return author;
    }

    public void updateAuthor(Long authorId, AuthorDTO authorDTO) {
        authorRepository.findById(authorId)
                .map(author -> {
                    author = dtoToEntity(authorDTO);
                    return authorRepository.save(author);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    public Boolean deleteAuthor(String firstName, String lastName) {
        Optional<Author> optionalAuthor = authorRepository.findByFirstNameAndLastName(firstName, lastName);

        if (optionalAuthor.isPresent()) {
            authorRepository.delete(optionalAuthor.get());
            return true;
        } else {
            return false;
        }

    }
}
