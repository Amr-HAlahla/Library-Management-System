package com.amr.training.securitydemo.repository;

import com.amr.training.securitydemo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a JOIN a.books b GROUP BY a.id HAVING COUNT(b) > :bookCount")
    List<Author> findByBooksCountGreaterThan(@Param("bookCount") int bookCount);

    @Query("SELECT DISTINCT a FROM Author a JOIN a.books b WHERE b.pages > :pageCount")
    List<Author> findAuthorsWithBooksHavingMoreThanPages(@Param("pageCount") int pageCount);

    List<Author> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    long count();
}
