package com.demoDesk.desk.repositories.specifications;

import com.demoDesk.desk.models.nomenclature.Element;
import org.springframework.data.jpa.domain.Specification;

public class ElementSpec {
    public static Specification<Element> titleContains(String word) {
        return (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + word.toLowerCase() + "%");
    }

    public static Specification<Element> findById (Long id) {
        return (Specification<Element>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Element> artContains (String word) {
        return (Specification<Element>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("art")), "%" + word.toLowerCase() + "%");
    }
}
