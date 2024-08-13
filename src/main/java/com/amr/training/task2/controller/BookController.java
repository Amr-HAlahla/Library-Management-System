package com.amr.training.task2.controller;

import com.amr.training.task2.dto.BookDTO;
import com.amr.training.task2.entity.Book;
import com.amr.training.task2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /*################################################################################*/
    @GetMapping("/authors/{author_id}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable Long author_id) {
        return bookService.getBooksByAuthor(author_id);
    }

    /*################################################################################*/

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }


    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}
