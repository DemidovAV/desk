package com.demoDesk.desk.services;

import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.EmployeeRepository;
import com.demoDesk.desk.repositories.TaskRepository;
import com.demoDesk.desk.repositories.TicketRepository;
import com.demoDesk.desk.repositories.specifications.TaskSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;

import javax.swing.text.html.HTMLDocument;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TaskServiceTest {
    @Configuration
    @Import(TaskService.class)
    public static class TestConfiguration {
    }
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private ElementRepository elementRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private TicketRepository ticketRepository;

    @Test
    void getTasksWithFiltering() {
        Specification<Task> specification = TaskSpec.findById(1L);
        List<Task> taskList = new ArrayList<>();
        taskList.add(Task.builder().build());
        Mockito.when(taskRepository.findAll(specification)).thenReturn(taskList);
        List<Task> expectedList = taskService.getTasksWithFiltering(specification);
        Assertions.assertEquals(expectedList, taskList);
    }
    @Test
    void getTasksWithFilteringThrowing() {
        Specification<Task> specification = TaskSpec.findById(1L);
        Mockito.when(taskRepository.findAll(specification)).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class, () -> taskService.getTasksWithFiltering(specification));
    }
}