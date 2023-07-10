package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.taskDto.TaskEditDto;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.EmployeeRepository;
import com.demoDesk.desk.repositories.TaskRepository;
import com.demoDesk.desk.repositories.specifications.TaskSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    private ElementRepository elementRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Autowired
    public void setElementRepository(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }
    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Task> getTasksWithFiltering(Specification<Task> specification) {
        return taskRepository.findAll(specification);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return taskRepository.findOne(TaskSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public void saveTask(Task task) {
        taskRepository.save(task);
    }


    public List<Task> sortTasksByExpirationDate (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getExpirationDate)).collect(Collectors.toList());
    }

    public List<Task> sortTasksByStatus (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getRequestStatus)).collect(Collectors.toList());
    }

    public List<Task> sortTasksByPriority (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getPriority)).collect(Collectors.toList());
    }

    public List<Task> sortTasksByCreationDate (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getCreationDate)).collect(Collectors.toList());
    }

    public List<Task> sortTasksByCloseDate (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getCloseDate)).collect(Collectors.toList());
    }

    public TaskEditDto prepareTaskEditDto(Long id) {
        TaskEditDto taskEditDto = new TaskEditDto();
        taskEditDto.setTask(findById(id));
        taskEditDto.setElements(elementRepository.findAll());
        taskEditDto.setEmployees(employeeRepository.findAll());
        return taskEditDto;
    }
}
