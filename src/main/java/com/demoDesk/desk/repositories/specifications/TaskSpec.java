package com.demoDesk.desk.repositories.specifications;

import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.queries.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class TaskSpec {
    public static Specification<Task> findById (Long id) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Task> executorContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Employee, Task>  employeeTask = root.join("executor");
            return  criteriaBuilder.like(criteriaBuilder.lower(employeeTask.get("name")), "%" + word.toLowerCase() + "%");
        };
    }

    public static Specification<Task> elementContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Element, Task> employeeTask = root.join("element");
            return criteriaBuilder.like(criteriaBuilder.lower(employeeTask.get("title")), "%" + word.toLowerCase() + "%");
        };
    }

}
