package com.demoDesk.desk.controllers;

import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;


//    private Specification<Department> filtration(String filter, String art) {
//        Specification<Department> spec = Specification.where(null);
//
//        if(filter != null) {
//            spec = spec.and(DepartmentSpec.titleContains(filter));
//        }
//        if (art != null) {
//            spec = spec.and(DepartmentSpec.artContains(art));
//        }
//        return spec;
//
//    }

    @GetMapping
    public List<Department> showDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/editDepartment/{id}")
    public Department showEditDepartment(@PathVariable(value="id") Long id){
        return departmentService.getDepartmentById(id);
    }

    @PostMapping("/editDepartment/confirm")
    public boolean editElementConfirm(@RequestBody Department department) {
        departmentService.saveDepartment(department);
        return true;
    }


    @GetMapping("/addDepartment")
    public Department addNewElement() {
        return new Department();
    }
    //
    @PostMapping("/addDepartment/confirm")
    public boolean addDepartmentConfirm(@RequestBody Department department) {
        departmentService.saveDepartment(department);
        return true;
    }

    @GetMapping("/showDepartment/{id}")
    public Department showOneDepartment(@PathVariable(value="id") Long id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping("/deleteDepartment/{id}")
    public boolean deleteDepartment(@PathVariable(value="id") Long id) {
        departmentService.deleteById(id);
        return true;
    }
}
