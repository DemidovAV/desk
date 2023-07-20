package com.demoDesk.desk.dto.departmentDto;

import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.queries.Task;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowDepartmentDto {
    private Department department;
    private List<Employee> employees;
}
