package com.demoDesk.desk.repositories.specifications;


import com.demoDesk.desk.models.personnel.Department;
import com.demoDesk.desk.models.personnel.Employee;
import com.demoDesk.desk.models.queries.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class EmployeeSpec {
    public static Specification<Employee> nameContains(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Employee> findById (Long id) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Employee> departmentTitleContains (String word) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Department, Employee> departmentEmployee = root.join("department");
            return  criteriaBuilder.like(criteriaBuilder.lower(departmentEmployee.get("title")), "%" + word.toLowerCase() + "%");
        };
    }

}
