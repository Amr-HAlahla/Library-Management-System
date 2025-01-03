package com.amr.training.library.repository;

import com.amr.training.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByPublisherId(Long publisherId);

    List<Book> findByPublishedDateAfter(LocalDate date);

    Long countBookByAuthorId(Long authorId);

    Long countBookByPublisherId(Long publisherId);
}
