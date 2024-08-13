package com.amr.training.task2.repository;

import com.amr.training.task2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByPublisherId(Long publisherId);

    List<Book> findByPublishedDateAfter(LocalDate date);

    Long countBookByAuthorId(Long authorId);

    Long countBookByPublisherId(Long publisherId);
}
