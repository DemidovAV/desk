package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.taskDto.TaskEditDto;
import com.demoDesk.desk.models.enums.RequestStatus;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.EmployeeRepository;
import com.demoDesk.desk.repositories.TaskRepository;
import com.demoDesk.desk.repositories.TicketRepository;
import com.demoDesk.desk.repositories.specifications.TaskSpec;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * тут аналогично с TicketService, не забудь про транзакции(они не везде должны быть)
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    private final ElementRepository elementRepository;

    private final EmployeeRepository employeeRepository;

    private final TicketRepository ticketRepository;


    public List<Task> getTasksWithFiltering(Specification<Task> specification) {
        return taskRepository.findAll(specification);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


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

    public List<Task> sortTasks(List<Task> tasks, String sortParameter) {
        switch (sortParameter) {
            case "expirationDate": {
                return tasks.stream().sorted(Comparator.comparing(Task::getExpirationDate)).collect(Collectors.toList());
            }
            case "closeDate": {
                return tasks.stream().sorted(Comparator.comparing(Task::getCloseDate)).collect(Collectors.toList());
            }
            case "creationDate": {
                return tasks.stream().sorted(Comparator.comparing(Task::getCreationDate)).collect(Collectors.toList());
            }
            case "status": {
                return tasks.stream().sorted(Comparator.comparing(Task::getRequestStatus)).collect(Collectors.toList());

            }
            case "priority": {
                return tasks.stream().sorted(Comparator.comparing(Task::getPriority)).collect(Collectors.toList());

            }
            default:
                return tasks;
        }

    }


    public TaskEditDto prepareTaskEditDto(Long id) {
        TaskEditDto taskEditDto = new TaskEditDto();
        Task task = findById(id);
        taskEditDto.setTask(task);
        taskEditDto.setElements(elementRepository.findAll().stream()
                .filter(e -> Objects.equals(e.getDepartment().getId(), task.getExecutor().getDepartment().getId()))
                .collect(Collectors.toList()));
        taskEditDto.setEmployees(employeeRepository.findAll().stream()
                .filter(e -> Objects.equals(e.getDepartment().getId(), task.getExecutor().getDepartment().getId()))
                .collect(Collectors.toList()));
        return taskEditDto;
    }

    public void changeTaskStatus(Long id, String status) {
        Task task = findById(id);
        switch (status) {
            case "inProgress":
                task.setRequestStatus(RequestStatus.IN_PROGRESS.getTitle());
                taskRepository.save(task);
                break;
            case "complete":
                task.setRequestStatus(RequestStatus.COMPLETE.getTitle());
                taskRepository.save(task);
                checkTicketForCompleteTasks(task.getTicketId());
                break;
            case "canceled":
                task.setRequestStatus(RequestStatus.CANCELED.getTitle());
                taskRepository.save(task);
                break;
        }
    }

    private void checkTicketForCompleteTasks(Long id) {
        Ticket ticket = ticketRepository.findOne(TicketSpec.findById(id)).orElse(null);
        assert ticket != null;
        List<Task> tasks = ticket.getTasks();
        boolean tasksCheck = false;
        for(Task t: tasks) {
            tasksCheck = t.getRequestStatus().equals(RequestStatus.COMPLETE.getTitle());
        }
        if (tasksCheck) {
            ticket.setRequestStatus(RequestStatus.COMPLETE.getTitle());
        }
        ticketRepository.save(ticket);
    }
}
