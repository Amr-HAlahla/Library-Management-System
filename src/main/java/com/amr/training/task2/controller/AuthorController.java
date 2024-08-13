package com.amr.training.task2.controller;

import com.amr.training.task2.dto.AuthorDTO;
import com.amr.training.task2.entity.Author;
import com.amr.training.task2.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author authorDetails) {
        return authorService.updateAuthor(id, authorDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }
}
