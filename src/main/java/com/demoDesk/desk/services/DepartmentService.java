package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.departmentDto.ShowDepartmentDto;
import com.demoDesk.desk.dto.employeeDto.AddNewEmployeeDto;
import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.personel.EmployeeStatus;
import com.demoDesk.desk.repositories.DepartmentRepository;
import com.demoDesk.desk.repositories.EmployeeRepository;
import com.demoDesk.desk.repositories.specifications.DepartmentSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    public List<Department> getDepartmentsWithFiltering(Specification<Department> specification) {
        return departmentRepository.findAll(specification);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findOne(DepartmentSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Transactional
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    public ShowDepartmentDto showDepartmentWithEmployees(Long id) {
        Department department = getDepartmentById(id);
        return ShowDepartmentDto.builder()
                .department(department)
                .employees(department.getEmployees())
                .build();
    }

    public void addNewEmployeeToDepartment(AddNewEmployeeDto newEmployeeDto) {
        Employee employee = Employee.builder()
                .department(departmentRepository.getReferenceById(newEmployeeDto.getDepartmentId()))
                .name(newEmployeeDto.getName())
                .status(EmployeeStatus.WORKING.getTitle())
                .build();
        employeeRepository.save(employee);
    }
}
