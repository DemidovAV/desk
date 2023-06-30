package com.demoDesk.desk.controllers;

import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import com.demoDesk.desk.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    private Specification<Element> filtration(String filter, String art) {
        Specification<Element> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(ElementSpec.titleContains(filter));
        }
        if (art != null) {
            spec = spec.and(ElementSpec.artContains(art));
        }
        return spec;

    }

    @GetMapping
    public List<Department> showDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/editDepartment/{id}")
    public Department showEditDepartment(@PathVariable(value="id") Long id){
        return departmentService.getDepartmentById(id);
    }

    @PostMapping("/editDepartment/confirm")
    public boolean editElementConfirm(@ModelAttribute(value="department") Department department) {
        departmentService.saveElement(department);
        return true;
    }


    @GetMapping("/addDepartment")
    public Department addNewElement() {
        return new Department();
    }
    //
    @PostMapping("/addDepartment/confirm")
    public boolean addDepartmentConfirm(@ModelAttribute(value="department") Department department) {
        departmentService.saveElement(department);
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
