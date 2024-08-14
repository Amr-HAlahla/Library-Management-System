package com.amr.training.task2.specification;

import com.amr.training.task2.entity.Author;
import com.amr.training.task2.entity.Book;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AuthorSpecification {

    public static Specification<Author> hasBooksWithPagesGreaterThan(int pages) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.join("books").get("pages"), pages));
    }

    public static Specification<Author> bornBefore(LocalDate date) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("birthDate"), date));
    }

    public static Specification<Author> hasBooksPublishedAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("books").get("publishedDate"), date);
        };
    }
}
