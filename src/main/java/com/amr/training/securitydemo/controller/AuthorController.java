package com.amr.training.securitydemo.controller;

import com.amr.training.securitydemo.dto.AuthorDTO;
import com.amr.training.securitydemo.entity.Author;
import com.amr.training.securitydemo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /*######################################################################*/
    @GetMapping("/birthdate-between")
    public ResponseEntity<?> getAuthorWithBirthDateBetween(
            @RequestParam("start_date") LocalDate startDate, @RequestParam("end_date") LocalDate endDate) {
        return authorService.getAuthorWithBirthDateBetween(startDate, endDate);
    }

    @GetMapping("/books-greater-than")
    public ResponseEntity<?> getAuthorByBookSizeGreater(@RequestParam("size") int size) {
        return authorService.getAuthorByBookSizeGreater(size);
    }

    @GetMapping("/books-more-than")
    public ResponseEntity<?> getAuthorByBooksCountGreater(@RequestParam("count") int count) {
        return authorService.getAuthorByBooksCountGreater(count);
    }
    /*######################################################################*/


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author authorDetails) {
        return authorService.updateAuthor(id, authorDetails);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }
}
