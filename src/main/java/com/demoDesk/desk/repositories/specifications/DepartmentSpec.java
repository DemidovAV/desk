package com.demoDesk.desk.repositories.specifications;

import com.demoDesk.desk.models.personnel.Department;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpec {
    public static Specification<Department> findById (Long id) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }
}
