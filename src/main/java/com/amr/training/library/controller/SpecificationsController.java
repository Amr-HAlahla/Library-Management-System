package com.amr.training.library.controller;


import com.amr.training.library.service.AuthorService;
import com.amr.training.library.service.BookService;
import com.amr.training.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/specifications")
public class SpecificationsController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final PublisherService publisherService;

    @Autowired
    public SpecificationsController(AuthorService authorService, BookService bookService, PublisherService publisherService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.publisherService = publisherService;
    }

    @GetMapping("/publishers/with-books-count")
    public ResponseEntity<?> getPublishersWithNumberOfBooks(@RequestParam("count") int count) {
        return publisherService.findPublishersWithBookCount(count);
    }

    @GetMapping("authors/books-published-after")
    public ResponseEntity<?> getAuthorsWithBooksPublishedAfter(@RequestParam("date") LocalDate date) {
        return authorService.hasBooksPublishedAfter(date);
    }

    @GetMapping("/books/author-and-publisher")
    public ResponseEntity<?> getBooksByAuthorAndPublisher(
            @RequestParam("author_id") Long authorId, @RequestParam("publisher_id") Long publisherId) {
        return bookService.hasAuthorAndPublisher(authorId, publisherId);
    }

    @GetMapping("/publishers/established-after")
    public ResponseEntity<?> publishersEstablishedAfter(@RequestParam("date") LocalDate date) {
        return publisherService.establishedAfter(date);
    }

    @GetMapping("/authors/born-before")
    public ResponseEntity<?> authorsBornBefore(@RequestParam("date") LocalDate date) {
        return authorService.authorsBornBefore(date);
    }

    @GetMapping("/books/title-contains")
    public ResponseEntity<?> BooksTitleContains(@RequestParam("pattern") String pattern) {
        return bookService.BooksTitleContains(pattern);
    }

    @GetMapping("/authors/with-books-more-than-pages")
    public ResponseEntity<?> getAuthorsWithBooksHavingPagesGreaterThan(@RequestParam("pages") int pages) {
        return ResponseEntity.ok(authorService.getAuthorsWithBooksHavingPagesGreaterThan(pages));
    }
}
