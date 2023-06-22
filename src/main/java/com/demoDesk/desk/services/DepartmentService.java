package com.demoDesk.desk.services;

import com.demoDesk.desk.models.Department;
import com.demoDesk.desk.models.Employee;
import com.demoDesk.desk.repositories.DepartmentRepository;
import com.demoDesk.desk.repositories.specifications.DepartmentSpec;
import com.demoDesk.desk.repositories.specifications.EmployeeSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartmentsWithFiltering(Specification<Department> specification) {
        return departmentRepository.findAll(specification);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Department getDepartmentById(Long id) {
        return departmentRepository.findOne(DepartmentSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Transactional
    public void saveElement(Department department) {
        departmentRepository.save(department);
    }
}
