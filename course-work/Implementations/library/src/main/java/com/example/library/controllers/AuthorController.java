package com.example.library.controllers;

import com.example.library.dtos.AuthorDTO;
import com.example.library.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/author", produces = "application/json")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Display an author by name")
    @ApiResponse(responseCode = "200",
            description = "Found the author",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/display")
    public ResponseEntity<?> getAuthorByName(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.ok(authorService.getAuthorByName(firstName, lastName));
    }

    @Operation(summary = "Create an author")
    @ApiResponse(responseCode = "201", description = "Created the author")
    @PostMapping("/create")
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update an author")
    @ApiResponse(responseCode = "200", description = "Updated the author")
    @PutMapping("/update")
    public ResponseEntity<?> updateAuthor(@RequestParam Long authorId, @RequestBody AuthorDTO authorDTO) {
        authorService.updateAuthor(authorId, authorDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Delete an author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the author"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAuthor(@RequestParam String firstName, @RequestParam String lastName) {
        Boolean isAuthorDeleted = authorService.deleteAuthor(firstName, lastName);

        if (isAuthorDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
