package com.demoDesk.desk.repositories.specifications;

import com.demoDesk.desk.models.Department;
import com.demoDesk.desk.models.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpec {
    public static Specification<Task> findById (Long id) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }
}
