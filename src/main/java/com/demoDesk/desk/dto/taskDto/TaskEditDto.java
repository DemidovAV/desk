package com.demoDesk.desk.dto.taskDto;

import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.queries.Task;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class TaskEditDto {
    private Task task;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Employee> employees;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Element> elements;
}
