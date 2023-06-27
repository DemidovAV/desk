package com.demoDesk.desk.repositories.specifications;

import com.demoDesk.desk.models.personel.Department;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpec {
    public static Specification<Department> findById (Long id) {
        return (Specification<Department>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }
}
