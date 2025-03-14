package com.example.library.services;

import com.example.library.dtos.LibraryDTO;
import com.example.library.entities.Book;
import com.example.library.entities.Library;
import com.example.library.repositories.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private final ModelMapper modelMapper;

    private final BookService bookService;

    public LibraryService(LibraryRepository libraryRepository, ModelMapper modelMapper, BookService bookService) {
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    public Library getOrCreateLibrary(String name) {
//        if (name == null) {
//            Library library = new Library();
//            libraryRepository.save(library);
//            return library;
//        }

        Optional<Library> optionalLibrary = libraryRepository.findByName(name);

        if (optionalLibrary.isPresent()) {
            return optionalLibrary.get();
        } else {
            Library library = new Library(name);
            libraryRepository.save(library);
            return library;
        }
    }

    public LibraryDTO getLibraryByName(String name) {
        return libraryRepository.findByName(name)
                .map(this::entityToDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void createLibrary(LibraryDTO libraryDTO) {
        Library library = dtoToEntity(libraryDTO);
        libraryRepository.save(library);
    }

    public void updateLibrary(Long libraryId, LibraryDTO libraryDTO) {
        libraryRepository.findById(libraryId)
                .map(library -> {
                    library = dtoToEntity(libraryDTO);
                    return libraryRepository.save(library);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    public Boolean deleteLibrary(String name) {
        Optional<Library> optionalLibrary = libraryRepository.findByName(name);

        if (optionalLibrary.isPresent()) {
            libraryRepository.delete(optionalLibrary.get());
            return true;
        } else {
            return false;
        }
    }

    private LibraryDTO entityToDto(Library library) {
        LibraryDTO libraryDTO = modelMapper.map(library, LibraryDTO.class);

        List<String> books = library.getBooks().stream().map(Book::getTitle).toList();
        libraryDTO.setBooks(books);

        return libraryDTO;
    }

    private Library dtoToEntity(LibraryDTO libraryDTO) {
        Library library = modelMapper.map(libraryDTO, Library.class);

        List<Book> books = new ArrayList<>();

        for (String bookTitle : libraryDTO.getBooks()) {
            Book book = bookService.getOrCreateBook(bookTitle);
            books.add(book);
        }

        library.setBooks(books);

        return library;
    }

}
