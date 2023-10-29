package com.demoDesk.desk.repositories.specifications;

import com.demoDesk.desk.models.nomenclature.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpec {
    public static Specification<Product> titleContains(String word) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + word.toLowerCase() + "%");
    }

    public static Specification<Product> findById (Long id) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Product> artContains (String word) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("art")), "%" + word.toLowerCase() + "%");
    }
}
