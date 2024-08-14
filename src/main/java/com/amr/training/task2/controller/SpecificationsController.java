package com.amr.training.task2.controller;


import com.amr.training.task2.service.AuthorService;
import com.amr.training.task2.service.BookService;
import com.amr.training.task2.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specifications")
public class SpecificationsController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final PublisherService publisherService;

    @Autowired
//    @Lazy
    public SpecificationsController(AuthorService authorService, BookService bookService, PublisherService publisherService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.publisherService = publisherService;
    }

    @GetMapping("/title-contains")
    public ResponseEntity<?> BooksTitleContains(@RequestParam("pattern") String pattern) {
        return bookService.BooksTitleContains(pattern);
    }

    @GetMapping("/with-books-more-than-pages")
    public ResponseEntity<?> getAuthorsWithBooksHavingPagesGreaterThan(@RequestParam("pages") int pages) {
        return ResponseEntity.ok(authorService.getAuthorsWithBooksHavingPagesGreaterThan(pages));
    }
}
