package com.amr.training.task2.specification;

import com.amr.training.task2.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookSpecifications {

    public static Specification<Book> titleContains(String word) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + word.toLowerCase() + "%"));
    }

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Book> hasAuthorName(String authorName) {
        return (root, query, cb) -> cb.like(root.join("author").get("name"), "%" + authorName + "%");
    }

    public static Specification<Book> hasPublisherName(String publisherName) {
        return (root, query, cb) -> cb.like(root.join("publisher").get("name"), "%" + publisherName + "%");
    }

    public static Specification<Book> publishedBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> cb.between(root.get("publishedDate"), startDate, endDate);
    }
}
