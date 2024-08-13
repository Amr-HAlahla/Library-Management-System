package com.amr.training.task2.service;

import com.amr.training.task2.dto.PublisherDTO;
import com.amr.training.task2.entity.Publisher;
import com.amr.training.task2.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

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
