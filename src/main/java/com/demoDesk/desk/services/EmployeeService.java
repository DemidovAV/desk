package com.demoDesk.desk.services;

import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.repositories.DepartmentRepository;
import com.demoDesk.desk.repositories.EmployeeRepository;
import com.demoDesk.desk.repositories.specifications.EmployeeSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;


    public List<Employee> getEmployeesWithFiltering(Specification<Employee> specification) {
        return employeeRepository.findAll(specification);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public Employee getEmployeeById(Long id) {
        return employeeRepository.findOne(EmployeeSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public List<Employee> sortEmployees(List<Employee> employees, String sortParameter) {
        switch (sortParameter) {
            case "department": {
                return employees.stream().sorted(Comparator.comparing(Employee::getDepartmentTitle)).collect(Collectors.toList());
            }
            case "status": {
                return employees.stream().sorted(Comparator.comparing(Employee::getStatus)).collect(Collectors.toList());
            }
            default:
                return employees;
        }

    }
}
