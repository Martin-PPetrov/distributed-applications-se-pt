package com.example.library.controllers;

import com.example.library.dtos.LibraryDTO;
import com.example.library.services.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/library", produces = "application/json")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Operation(summary = "Display a library by name")
    @ApiResponse(responseCode = "200",
            description = "Found the library",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/display")
    public ResponseEntity<?> getLibraryByName(@RequestParam String name) {
        return ResponseEntity.ok(libraryService.getLibraryByName(name));
    }

    @Operation(summary = "Create a library")
    @ApiResponse(responseCode = "201", description = "Created the library")
    @PostMapping("/create")
    public ResponseEntity<?> createLibrary(@RequestBody LibraryDTO libraryDTO) {
        libraryService.createLibrary(libraryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a library")
    @ApiResponse(responseCode = "200", description = "Updated the library")
    @PutMapping("/update")
    public ResponseEntity<?> updateLibrary(@RequestParam Long libraryId, @RequestBody LibraryDTO libraryDTO) {
        libraryService.updateLibrary(libraryId, libraryDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Delete a library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the library"),
            @ApiResponse(responseCode = "404", description = "Library not found"),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLibrary(@RequestParam String name) {
        Boolean isLibraryDeleted = libraryService.deleteLibrary(name);

        if (isLibraryDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
