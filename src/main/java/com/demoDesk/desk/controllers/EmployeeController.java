package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.employeeDto.ShowEmployeesDto;
import com.demoDesk.desk.models.personnel.Employee;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.repositories.specifications.EmployeeSpec;
import com.demoDesk.desk.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    private Specification<Employee> filtration(String name, String departmentTitle) {
        Specification<Employee> spec = Specification.where(null);
        if(name != null) {
            spec = spec.and(EmployeeSpec.nameContains(name));
        }
        if(departmentTitle != null) {
            spec = spec.and((EmployeeSpec.departmentTitleContains(departmentTitle)));
        }
        return spec;
    }

    //Получаем список сотрудников, можем отфильтровать по имени
    @GetMapping
    public ShowEmployeesDto showEmployees(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "depTitle", required = false) String departmentTitle) {
        return ShowEmployeesDto.builder()
                .name(name)
                .employees(employeeService.getEmployeesWithFiltering(filtration(name, departmentTitle)))
                .build();
    }

    //Сортировка отфильтрованного списка по параметру 'sort': department или status.
    @PostMapping
    public ShowEmployeesDto showEmployeesSorted(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "depTitle", required = false) String departmentTitle,
                                                @RequestParam(value = "sort") String sort) {
        List<Employee> employees = employeeService.getEmployeesWithFiltering(filtration(name, departmentTitle));
        return ShowEmployeesDto.builder()
                .name(name)
                .employees(employeeService.sortEmployees(employees, sort))
                .build();
    }

    //Сброс фильтров и сортировки
    @PostMapping("/reset")
    public ShowEmployeesDto showEmployeesListReset() {
        return ShowEmployeesDto.builder()
                .name(null)
                .employees(employeeService.getAllEmployees())
                .build();
    }

    //Получаем информацию о конкретном сотруднике по его id
    @GetMapping("/showEmployee/{id}")
    public Employee showOneEmployee(@PathVariable(value="id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    //Получаем список тасков выбранного сотрудника
    @PostMapping("/showEmployee/{id}/getTasks")
    public List<Task> employeeGetTasks(@PathVariable(value="id") Long id,
                                       @RequestParam(value = "requestStatus") String requestStatus) {
        return employeeService.getTasksByRequestStatus(id, requestStatus);
    }

    //Выставляем статус выбранному сотруднику параметром 'status': ill, working или vacation
    @PostMapping("/showEmployee/{id}/setStatus")
    public boolean employeeSetStatus(@PathVariable(value="id") Long id,
                                     @RequestParam(value = "status") String status) {
        employeeService.employeeSetStatus(id, status);
        return true;
    }

    @PostMapping("/deleteEmployee/{id}")
    public boolean deleteEmployee(@PathVariable(value = "id") Long id) {
        employeeService.deleteById(id);
        return true;
    }

}
