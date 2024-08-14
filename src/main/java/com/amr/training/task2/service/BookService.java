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
import com.amr.training.task2.specification.BookSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    /*############################### Specifications #######################################*/

    public ResponseEntity<?> hasAuthorAndPublisher(Long authorId, Long publisherId) {
        Specification<Book> spec = BookSpecifications.hasAuthorAndPublisher(authorId, publisherId);
        List<Book> bookList = bookRepository.findAll(spec);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok("No Books with Author id = " + authorId +
                    " and Publisher id = " + publisherId);
        }
        List<BookDTO> bookDTOS = bookList.stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(bookDTOS);
    }

    public ResponseEntity<?> BooksTitleContains(String word) {
        Specification<Book> spec = BookSpecifications.titleContains(word);
        List<Book> bookList = bookRepository.findAll(spec);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok("No books with title contains the pattern - " + word);
        }
        List<BookDTO> bookDTOS = bookList.stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(bookDTOS);
    }

    /*######################################################################################*/

    /*############################### NEW OPERATIONS #######################################*/
    /* Get books by author id*/
    public ResponseEntity<?> getBooksByAuthor(Long authorId) {
        List<Book> bookList = bookRepository.findByAuthorId(authorId);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok("No books for the author with id - " + authorId);
        }
        // Convert list of Book to list of BookDTO using stream
        List<BookDTO> bookDTOList = bookList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(bookDTOList);
    }

    /*Get Books by Publisher id*/
    public ResponseEntity<?> getBooksByPublisher(Long publisherId) {
        List<Book> bookList = bookRepository.findByPublisherId(publisherId);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok("No books for the publisher with id - " + publisherId);
        }
        // Convert list of Book to list of BookDTO using stream
        List<BookDTO> bookDTOList = bookList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(bookDTOList);
    }

    /*Get Books published after a certain date*/
    public ResponseEntity<?> getBooksPublishedAfterDate(LocalDate date) {
        List<Book> bookList = bookRepository.findByPublishedDateAfter(date);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok("No books published after - " + date);
        }
        // Convert list of Book to list of BookDTO using stream
        List<BookDTO> bookDTOList = bookList.stream()
                .map(this::convertToDTO)  // Convert each Book to BookDTO
                .collect(Collectors.toList());  // Collect the results into a list

        return ResponseEntity.ok(bookDTOList);
    }

    /*Count Books created by an author*/
    public ResponseEntity<?> countBooksByAuthor(Long authorId) {
        // Check the existence of the author.
        if (!authorRepository.existsById(authorId)) {
            return ResponseEntity.badRequest().body("Invalid Author Id: " + authorId);
        }
        Long numberOfBooks = bookRepository.countBookByAuthorId(authorId);
        return ResponseEntity.ok(numberOfBooks);
    }

    /*Count Books by publisher*/
    public ResponseEntity<?> countBooksByPublisher(Long publisherId) {
        // Check the existence of the author.
        if (!publisherRepository.existsById(publisherId)) {
            return ResponseEntity.badRequest().body("Invalid Publisher Id: " + publisherId);
        }
        Long numberOfBooks = bookRepository.countBookByPublisherId(publisherId);
        return ResponseEntity.ok(numberOfBooks);
    }

    /*######################################################################*/
    /*############################## CRUD OPERATIONS ########################################*/
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
        // Check if the book exists
        Optional<Book> existingBookOpt = bookRepository.findById(id);
        if (existingBookOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Book not found with id: " + id);
        }

        // Check if the author exists
        if (!authorRepository.existsById(bookDetails.getAuthor().getId())) {
            return ResponseEntity.badRequest().body("Invalid Author Id: " + bookDetails.getAuthor().getId());
        }

        // Check if the publisher exists
        if (!publisherRepository.existsById(bookDetails.getPublisher().getId())) {
            return ResponseEntity.badRequest().body("Invalid Publisher Id: " + bookDetails.getPublisher().getId());
        }

        // Update the existing book
        Book book = existingBookOpt.get();
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
    /*######################################################################*/
}
