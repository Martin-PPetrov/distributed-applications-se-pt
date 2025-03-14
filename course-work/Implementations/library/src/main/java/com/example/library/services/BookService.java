package com.example.library.services;

import com.example.library.dtos.BookDisplayDTO;
import com.example.library.dtos.BookDTO;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Library;
import com.example.library.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    private final AuthorService authorService;

    private final LibraryService libraryService;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper, @Lazy AuthorService authorService, @Lazy LibraryService libraryService) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.libraryService = libraryService;
    }

    public BookDisplayDTO getBookByTitle(String title) {
//        Book byTitle = bookRepository.findByTitle(title)
//                .orElseThrow();
//
//        return entityToDto(byTitle);

        return bookRepository.findByTitle(title)
                .map(this::entityToDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void createBook(BookDTO bookDTO) {
        Book book = dtoToEntity(bookDTO);
        bookRepository.save(book);
    }

    public void updateBook(Long bookId, BookDTO bookDTO) {
        bookRepository.findById(bookId)
                .map(book -> {
                    book = dtoToEntity(bookDTO);
                    return bookRepository.save(book);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    public Boolean deleteBook(String title) {
        Optional<Book> optionalBook = bookRepository.findByTitle(title);

        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
            return true;
        } else {
            return false;
        }
    }

    public Book getOrCreateBook(String title) {
        Optional<Book> optionalBook = bookRepository.findByTitle(title);

        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            Book book = new Book(title);
            bookRepository.save(book);
            return book;
        }
    }

    private BookDisplayDTO entityToDto(Book book) {
        BookDisplayDTO bookDisplayDTO = modelMapper.map(book, BookDisplayDTO.class);

        Author author = book.getAuthor();
        bookDisplayDTO.setAuthor(author.getFirstName() + " " + author.getLastName());

        return bookDisplayDTO;
    }

    private Book dtoToEntity(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);

        book.setAuthor(authorService.getOrCreateAuthor(bookDTO.getAuthor()));
        book.getAuthor().getBooks().add(book);

        List<Library> libraries = new ArrayList<>();

        for (String libraryName : bookDTO.getLibraries()) {
            Library library = libraryService.getOrCreateLibrary(libraryName);
            libraries.add(library);
        }

        book.setLibraries(libraries);

        return book;
    }

}
