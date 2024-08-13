package com.amr.training.task2.service;

import com.amr.training.task2.dto.AuthorDTO;
import com.amr.training.task2.entity.Author;
import com.amr.training.task2.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            return ResponseEntity.badRequest().body("Author not found with id: " + id);
        }
        return ResponseEntity.ok(convertToDTO(author.get()));
    }


    public ResponseEntity<?> createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(convertToDTO(savedAuthor));
    }

    public ResponseEntity<?> updateAuthor(Long id, Author authorDetails) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isEmpty()) {
            return ResponseEntity.badRequest().body("Author not found with id: " + id);
        }
        Author author = existingAuthor.get();
        author.setName(authorDetails.getName());
        author.setBirthDate(authorDetails.getBirthDate());

        Author updatedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(convertToDTO(updatedAuthor));
    }

    public ResponseEntity<String> deleteAuthor(Long id) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isEmpty()) {
            return ResponseEntity.badRequest().body("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
        return ResponseEntity.ok("Author deleted successfully");
    }

    private AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(author.getId(), author.getName(), author.getBirthDate());
    }
}
