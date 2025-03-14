package com.example.library.controllers;

import com.example.library.dtos.BookDTO;
import com.example.library.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/book", produces = "application/json")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Display a book by name")
    @ApiResponse(responseCode = "200",
            description = "Found the book",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/display")
    public ResponseEntity<?> getBookByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @Operation(summary = "Create a book")
    @ApiResponse(responseCode = "201", description = "Created the book")
    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO) {
        bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a book")
    @ApiResponse(responseCode = "200", description = "Updated the book")
    @PutMapping("/update")
    public ResponseEntity<?> updateBook(@RequestParam Long bookId, @RequestBody BookDTO bookDTO) {
        bookService.updateBook(bookId, bookDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBook(@RequestParam String title) {
        Boolean isBookDeleted = bookService.deleteBook(title);

        if (isBookDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
