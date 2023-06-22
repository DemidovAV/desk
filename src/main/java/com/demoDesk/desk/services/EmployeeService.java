package com.demoDesk.desk.services;

import com.demoDesk.desk.models.Element;
import com.demoDesk.desk.models.Employee;
import com.demoDesk.desk.repositories.EmployeeRepository;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import com.demoDesk.desk.repositories.specifications.EmployeeSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployeesWithFiltering(Specification<Employee> specification) {
        return employeeRepository.findAll(specification);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findOne(EmployeeSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void saveElement(Employee employee) {
        employeeRepository.save(employee);
    }
}
