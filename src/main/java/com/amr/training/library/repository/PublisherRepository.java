package com.amr.training.library.repository;

import com.amr.training.library.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long>, JpaSpecificationExecutor<Publisher> {

    List<Publisher> findByEstablishedDateAfter(LocalDate date);

    @Query("SELECT p FROM Publisher p JOIN p.books b GROUP BY p.id HAVING COUNT(b) = :bookCount")
    List<Publisher> findByBooksCount(@Param("bookCount") int bookCount);

    long count();
}
