package com.amr.training.task2.repository;

import com.amr.training.task2.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a JOIN a.books b GROUP BY a.id HAVING COUNT(b) > :bookCount")
    List<Author> findByBooksSizeGreaterThan(@Param("bookCount") int bookCount);

    List<Author> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    long count();
}
