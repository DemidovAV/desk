package com.demoDesk.desk.dto.taskDto;

import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.queries.Task;
import lombok.Data;

import java.util.List;

@Data
public class TaskEditDto {
    private Task task;
    private List<Employee> employees;
    private List<Element> elements;
}
