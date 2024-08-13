package com.amr.training.task2.controller;

import com.amr.training.task2.dto.PublisherDTO;
import com.amr.training.task2.entity.Publisher;
import com.amr.training.task2.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }


    /*##############################################################################*/
    @GetMapping("/count")
    public ResponseEntity<Long> getNumberOfPublishers() {
        return publisherService.getNumberOfPublishers();
    }

    @GetMapping("/books-count")
    public ResponseEntity<?> getPublisherWithBooksCount(@RequestParam("count") int count) {
        return publisherService.getPublisherWithBooksCount(count);
    }

    @GetMapping("/published-after")
    public ResponseEntity<?> getPublishersEstablishedAfterDate(@RequestParam("date") LocalDate date) {
        return publisherService.getPublishersEstablishedAfterDate(date);
    }

    /*##############################################################################*/

    @GetMapping
    public List<PublisherDTO> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id);
    }


    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody Publisher publisher) {
        return publisherService.createPublisher(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, @RequestBody Publisher publisherDetails) {
        return publisherService.updatePublisher(id, publisherDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable Long id) {
        return publisherService.deletePublisher(id);
    }
}
