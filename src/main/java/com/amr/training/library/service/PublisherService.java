package com.amr.training.library.service;

import com.amr.training.library.dto.PublisherDTO;
import com.amr.training.library.entity.Publisher;
import com.amr.training.library.repository.PublisherRepository;
import com.amr.training.library.specification.PublisherSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, BookService bookService) {
        this.publisherRepository = publisherRepository;
    }

    /*##############################################################################*/
    /*############################ SPECIFICATIONS ##################################*/
    public ResponseEntity<?> findPublishersWithBookCount(int  bookCount) {
        Specification<Publisher> spec = PublisherSpecifications.hasBookCount(bookCount);
        List<Publisher> publishers = publisherRepository.findAll(spec);
        if (publishers.isEmpty()) {
            return ResponseEntity.ok("No publishers have published `" + bookCount + "` books exactly!");
        }
        List<PublisherDTO> publisherDTOS = publishers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(publisherDTOS);
    }

    public ResponseEntity<?> establishedAfter(LocalDate date) {
        Specification<Publisher> spec = PublisherSpecifications.establishedAfter(date);
        List<Publisher> publishers = publisherRepository.findAll(spec);
        if (publishers.isEmpty()) {
            return ResponseEntity.ok("No publishers established after - " + date);
        }
        List<PublisherDTO> publisherDTOS = publishers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(publisherDTOS);
    }

    /*##############################################################################*/
    /*##############################################################################*/
    public ResponseEntity<Long> getNumberOfPublishers() {
        return ResponseEntity.ok(publisherRepository.count());
    }

    /*Find publishers by Books count. */
    public ResponseEntity<?> getPublisherWithBooksCount(int count) {
        List<Publisher> publishers = publisherRepository.findByBooksCount(count);
        if (publishers.isEmpty()) {
            return ResponseEntity.ok("No publishers with number of Books = " + count);
        }
        List<PublisherDTO> publisherDTOS = publishers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(publisherDTOS);
    }

    public ResponseEntity<?> getPublishersEstablishedAfterDate(LocalDate date) {
        List<Publisher> publishers = publisherRepository.findByEstablishedDateAfter(date);
        if (publishers.isEmpty()) {
            return ResponseEntity.ok("No publishers established after - " + date);
        }
        // Convert list of Book to list of BookDTO using stream
        List<PublisherDTO> publisherDTOS = publishers.stream()
                .map(this::convertToDTO)  // Convert each Publisher to PublisherDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(publisherDTOS);
    }
    /*##############################################################################*/

    public List<PublisherDTO> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> getPublisherById(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isEmpty()) {
            return ResponseEntity.badRequest().body("Publisher not found with id: " + id);
        }
        return ResponseEntity.ok(convertToDTO(publisher.get()));
    }


    public ResponseEntity<?> createPublisher(Publisher publisher) {
        Publisher savedPublisher = publisherRepository.save(publisher);
        return ResponseEntity.ok(convertToDTO(savedPublisher));
    }

    public ResponseEntity<?> updatePublisher(Long id, Publisher publisherDetails) {
        Optional<Publisher> existingPublisher = publisherRepository.findById(id);
        if (existingPublisher.isEmpty()) {
            return ResponseEntity.badRequest().body("Publisher not found with id: " + id);
        }
        Publisher publisher = existingPublisher.get();
        publisher.setName(publisherDetails.getName());
        publisher.setEstablishedDate(publisherDetails.getEstablishedDate());

        Publisher updatedPublisher = publisherRepository.save(publisher);
        return ResponseEntity.ok(convertToDTO(updatedPublisher));
    }

    public ResponseEntity<String> deletePublisher(Long id) {
        Optional<Publisher> existingPublisher = publisherRepository.findById(id);
        if (existingPublisher.isEmpty()) {
            return ResponseEntity.badRequest().body("Publisher not found with id: " + id);
        }
        publisherRepository.deleteById(id);
        return ResponseEntity.ok("Publisher deleted successfully");
    }

    private PublisherDTO convertToDTO(Publisher publisher) {
        return new PublisherDTO(publisher.getId(), publisher.getName(), publisher.getEstablishedDate());
    }
}
