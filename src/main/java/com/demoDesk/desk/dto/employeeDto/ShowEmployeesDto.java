package com.demoDesk.desk.dto.employeeDto;

import com.demoDesk.desk.models.personel.Employee;
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
    private List<Employee> employees;

}
