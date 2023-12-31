package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.departmentDto.ShowDepartmentDto;
import com.demoDesk.desk.dto.employeeDto.AddNewEmployeeDto;
import com.demoDesk.desk.models.personnel.Department;
import com.demoDesk.desk.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<Department> showDepartments() {
        return departmentService.getAllDepartments();
    }


    @PostMapping("/editOrAddDepartment/confirm")
    public boolean editElementConfirm(@RequestBody Department department) {
        departmentService.saveDepartment(department);
        return true;
    }


    @GetMapping("/getDepartmentInfo/{id}")
    public ShowDepartmentDto showOneDepartment(@PathVariable(value="id") Long id) {
        return departmentService.showDepartmentWithEmployees(id);
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public boolean deleteDepartment(@PathVariable(value="id") Long id) {
        departmentService.deleteById(id);
        return true;
    }

    @PostMapping("/saveNewEmployeeInDepartment/")
    public boolean saveNewEmployeeInDepartment(@RequestBody AddNewEmployeeDto newEmployeeDto) {
        departmentService.addNewEmployeeToDepartment(newEmployeeDto);
        return true;
    }

}
