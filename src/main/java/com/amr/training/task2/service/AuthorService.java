package com.amr.training.task2.service;

import com.amr.training.task2.dto.AuthorDTO;
import com.amr.training.task2.dto.BookDTO;
import com.amr.training.task2.entity.Author;
import com.amr.training.task2.entity.Book;
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

    /*######################################################################*/
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
