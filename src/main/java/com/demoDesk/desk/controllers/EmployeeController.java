package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.employeeDto.ShowEmployeesDto;
import com.demoDesk.desk.dto.productDto.ShowProductsDto;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.repositories.specifications.EmployeeSpec;
import com.demoDesk.desk.services.EmployeeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    private Specification<Employee> filtration(String name) {
        Specification<Employee> spec = Specification.where(null);
        if(name != null) {
            spec = spec.and(EmployeeSpec.nameContains(name));
        }
        return spec;
    }

    @GetMapping
    public ShowEmployeesDto showEmployees(@RequestParam(value = "name", required = false) String name) {
        return ShowEmployeesDto.builder()
                .name(name)
                .employees(employeeService.getEmployeesWithFiltering(filtration(name)))
                .build();
    }

    @PostMapping
    public ShowEmployeesDto showEmployeesSorted(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "sort") String sort) {
        List<Employee> employees = employeeService.getEmployeesWithFiltering(filtration(name));
        return ShowEmployeesDto.builder()
                .name(name)
                .employees(employeeService.sortEmployees(employees, sort))
                .build();
    }

    @PostMapping("/reset")
    public ShowEmployeesDto showEmployeesListReset() {
        return ShowEmployeesDto.builder()
                .name(null)
                .employees(employeeService.getAllEmployees())
                .build();
    }

}
