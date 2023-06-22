package com.demoDesk.desk.services;

import com.demoDesk.desk.models.Task;
import com.demoDesk.desk.models.Ticket;
import com.demoDesk.desk.repositories.TaskRepository;
import com.demoDesk.desk.repositories.specifications.TaskSpec;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
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

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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


    public List<Task> sortTicketsByExpirationDate (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getExpirationDate)).collect(Collectors.toList());
    }

    public List<Task> sortTicketsByStatus (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getRequestStatus)).collect(Collectors.toList());
    }

    public List<Task> sortTicketsByPriority (List<Task> tasks){
        return tasks.stream().sorted(Comparator.comparing(Task::getPriority)).collect(Collectors.toList());
    }
}
