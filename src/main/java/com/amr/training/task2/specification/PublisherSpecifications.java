package com.amr.training.task2.specification;

import com.amr.training.task2.entity.Publisher;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PublisherSpecifications {

    public static Specification<Publisher> establishedAfter(LocalDate date) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("establishedDate"), date));
    }

    public static Specification<Publisher> hasBookCount(int count) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.size(root.get("books")), count);
    }


}
