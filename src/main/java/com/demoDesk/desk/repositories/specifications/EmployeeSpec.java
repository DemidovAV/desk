package com.demoDesk.desk.repositories.specifications;


import com.demoDesk.desk.models.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpec {
    public static Specification<Employee> nameContains(String name) {
        return (Specification<Employee>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Employee> findById (Long id) {
        return (Specification<Employee>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Employee> artContains (String word) {
        return (Specification<Employee>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("art")), "%" + word.toLowerCase() + "%");
    }
}
