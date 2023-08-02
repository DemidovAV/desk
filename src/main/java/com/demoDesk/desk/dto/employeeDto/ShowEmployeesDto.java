package com.demoDesk.desk.dto.employeeDto;

import com.demoDesk.desk.models.personnel.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowEmployeesDto {
    private String name;
    private String departmentTitle;
    private List<Employee> employees;

}
