package com.amr.training.task2.service;

import com.amr.training.task2.dto.BookDTO;
import com.amr.training.task2.dto.AuthorDTO;
import com.amr.training.task2.dto.PublisherDTO;
import com.amr.training.task2.entity.Book;
import com.amr.training.task2.entity.Author;
import com.amr.training.task2.entity.Publisher;
import com.amr.training.task2.repository.AuthorRepository;
import com.amr.training.task2.repository.BookRepository;
import com.amr.training.task2.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.badRequest().body("Book not found with id: " + id);
        }

        Book bookEntity = book.get();
        AuthorDTO authorDTO = new AuthorDTO(bookEntity.getAuthor().getId(), bookEntity.getAuthor().getName(), bookEntity.getAuthor().getBirthDate());
        PublisherDTO publisherDTO = new PublisherDTO(bookEntity.getPublisher().getId(), bookEntity.getPublisher().getName(), bookEntity.getPublisher().getEstablishedDate());
        BookDTO bookDTO = new BookDTO(bookEntity.getId(), bookEntity.getTitle(), bookEntity.getIsbn(), bookEntity.getPublishedDate(), bookEntity.getPages(), authorDTO, publisherDTO);

        return ResponseEntity.ok(bookDTO);
    }


    public ResponseEntity<?> createBook(Book book) {
        if (!authorRepository.existsById(book.getAuthor().getId())) {
            return ResponseEntity.badRequest().body("Invalid Author Id: " + book.getAuthor().getId());
        }
        if (!publisherRepository.existsById(book.getPublisher().getId())) {
            return ResponseEntity.badRequest().body("Invalid Publisher Id: " + book.getPublisher().getId());
        }

        // Retrieve and set the full Author and Publisher objects
        book.setAuthor(authorRepository.findById(book.getAuthor().getId()).orElse(null));
        book.setPublisher(publisherRepository.findById(book.getPublisher().getId()).orElse(null));

        Book savedBook = bookRepository.save(book);
        return ResponseEntity.ok(convertToDTO(savedBook));
    }

    public ResponseEntity<?> updateBook(Long id, Book bookDetails) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) {
            return ResponseEntity.badRequest().body("Book not found with id: " + id);
        }

        Book book = existingBook.get();
        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublishedDate(bookDetails.getPublishedDate());
        book.setPages(bookDetails.getPages());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());

        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(convertToDTO(updatedBook));
    }

    public ResponseEntity<String> deleteBook(Long id) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) {
            return ResponseEntity.badRequest().body("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    private BookDTO convertToDTO(Book book) {
        AuthorDTO authorDTO = new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName(), book.getAuthor().getBirthDate());
        PublisherDTO publisherDTO = new PublisherDTO(book.getPublisher().getId(), book.getPublisher().getName(), book.getPublisher().getEstablishedDate());
        return new BookDTO(book.getId(), book.getTitle(), book.getIsbn(), book.getPublishedDate(), book.getPages(), authorDTO, publisherDTO);
    }
}
