package com.amr.training.library.service;

import com.amr.training.library.dto.AuthorDTO;
import com.amr.training.library.entity.Author;
import com.amr.training.library.repository.AuthorRepository;
import com.amr.training.library.specification.AuthorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /*######################################################################*/
    /*############# Using Specifications ###################################*/
    public ResponseEntity<?> hasBooksPublishedAfter(LocalDate date) {
        Specification<Author> spec = AuthorSpecification.hasBooksPublishedAfter(date);
        List<Author> authorList = authorRepository.findAll(spec);
        if (authorList.isEmpty()) {
            return ResponseEntity.ok("No Authors have books published after - " + date);
        }
        // Convert list of Book to list of BookDTO using stream
        List<AuthorDTO> authorDTOS = authorList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(authorDTOS);
    }

    public ResponseEntity<?> authorsBornBefore(LocalDate date) {
        Specification<Author> spec = AuthorSpecification.bornBefore(date);
        List<Author> authorList = authorRepository.findAll(spec);
        if (authorList.isEmpty()) {
            return ResponseEntity.ok("No Authors born before - " + date);
        }
        // Convert list of Book to list of BookDTO using stream
        List<AuthorDTO> authorDTOS = authorList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(authorDTOS);
    }

    public ResponseEntity<?> getAuthorsWithBooksHavingPagesGreaterThan(int pages) {
        Specification<Author> specification = AuthorSpecification.hasBooksWithPagesGreaterThan(pages);
        List<Author> authorList = authorRepository.findAll(specification);
        if (authorList.isEmpty()) {
            return ResponseEntity.ok("No Authors have Books with pages greater than - " + pages);
        }
        // Convert list of Book to list of BookDTO using stream
        List<AuthorDTO> authorDTOS = authorList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(authorDTOS);
    }
    /*######################################################################*/

    /*######################################################################*/
    /*Find Authors with Birthdate in a range*/
    public ResponseEntity<?> getAuthorWithBirthDateBetween(
            LocalDate startDate, LocalDate endDate) {
        List<Author> authorList = authorRepository.findByBirthDateBetween(startDate, endDate);
        if (authorList.isEmpty()) {
            return ResponseEntity.ok("No Authors with BirthDate between " + startDate + " - " + endDate);
        }
        // Convert list of Book to list of BookDTO using stream
        List<AuthorDTO> authorDTOS = authorList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(authorDTOS);
    }

    // Get Authors have books greater than a certain amount.
    public ResponseEntity<?> getAuthorByBooksCountGreater(int count) {
        List<Author> authorList = authorRepository.findByBooksCountGreaterThan(count);
        if (authorList.isEmpty()) {
            return ResponseEntity.ok("No Authors with number of books greater than - " + count);
        }
        // Convert list of Book to list of BookDTO using stream
        List<AuthorDTO> authorDTOS = authorList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(authorDTOS);
    }

    // Get Authors have Books with number of pages greater than a certain number.
    public ResponseEntity<?> getAuthorByBookSizeGreater(int bookSize) {
        List<Author> authorList = authorRepository.findAuthorsWithBooksHavingMoreThanPages(bookSize);
        if (authorList.isEmpty()) {
            return ResponseEntity.ok("No Authors with Book size greater than - " + bookSize);
        }
        // Convert list of Book to list of BookDTO using stream
        List<AuthorDTO> authorDTOS = authorList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(authorDTOS);
    }
    /*######################################################################*/


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
