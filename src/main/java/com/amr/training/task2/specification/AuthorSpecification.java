package com.amr.training.task2.specification;

import com.amr.training.task2.entity.Author;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecification {

    public static Specification<Author> hasBooksWithPagesGreaterThan(int pages) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.join("books").get("pages"), pages));
    }
}
