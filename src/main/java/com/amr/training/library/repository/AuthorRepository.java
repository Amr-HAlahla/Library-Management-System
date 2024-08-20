package com.amr.training.library.repository;

import com.amr.training.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    @Query("SELECT a FROM Author a JOIN a.books b GROUP BY a.id HAVING COUNT(b) > :bookCount")
    List<Author> findByBooksCountGreaterThan(@Param("bookCount") int bookCount);

    @Query("SELECT DISTINCT a FROM Author a JOIN a.books b WHERE b.pages > :pageCount")
    List<Author> findAuthorsWithBooksHavingMoreThanPages(@Param("pageCount") int pageCount);

    List<Author> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    long count();
}
