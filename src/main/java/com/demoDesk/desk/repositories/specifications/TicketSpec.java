package com.demoDesk.desk.repositories.specifications;

import com.demoDesk.desk.models.queries.Ticket;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpec {
    public static Specification<Ticket> findById (Long id) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Ticket> titleContains(String word) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + word.toLowerCase() + "%");
    }
}
