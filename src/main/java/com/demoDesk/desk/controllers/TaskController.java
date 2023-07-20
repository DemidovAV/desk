package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.taskDto.ShowTasksDto;
import com.demoDesk.desk.dto.taskDto.TaskEditDto;
import com.demoDesk.desk.dto.ticketDto.ShowTicketsDto;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.specifications.TaskSpec;
import com.demoDesk.desk.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private Specification<Task> spec(String executor, String element) {
        Specification<Task> spec = Specification.where(null);

        if(executor != null) {
            spec = spec.and(TaskSpec.executorContains(executor));
        }
        if(element != null) {
            spec = spec.and(TaskSpec.elementContains(element));
        }
        return spec;
    }

    //Показывает страницу со списком уже существующих тасков
    @GetMapping
    public ShowTasksDto showTasks(@RequestParam(value = "executor", required = false) String executor,
                                      @RequestParam(value = "element", required = false) String element){
        ShowTasksDto showTasksDto = new ShowTasksDto();
        showTasksDto.setTasksList(taskService.getTasksWithFiltering(spec(executor, element)));
        showTasksDto.setExecutorFilter(executor);
        showTasksDto.setElementFilter(element);
        return showTasksDto;
    }

    //Сортируем отфильтрованные таски

    @PostMapping
    public ShowTasksDto sortTasks(@RequestParam(value = "executor", required = false) String executor,
                                    @RequestParam(value = "element", required = false) String element,
                                    @RequestParam(value = "sort") String sortBy){
        List<Task> filteredTasks= taskService.getTasksWithFiltering(spec(executor, element));

        return   ShowTasksDto.builder()
                .tasksList(taskService.sortTasks(filteredTasks, sortBy))
                .executorFilter(executor)
                .elementFilter(element)
                .build();
    }

    //Сброс фильтров
    @PostMapping("/reset")
    public ShowTasksDto resetShowTickets() {
        ShowTasksDto showTasksDto = new ShowTasksDto();
        showTasksDto.setTasksList(taskService.getAllTasks());
        showTasksDto.setExecutorFilter(null);
        showTasksDto.setElementFilter(null);
        return showTasksDto;
    }


    //Просмотр отдельного таска
    @GetMapping("/showTask/{id}")
    public Task showTask (@PathVariable(value = "id") Long id) {
        return taskService.findById(id);
    }

    //Удалить выбранный тикет
    @DeleteMapping ("/delete/{id}")
    public boolean deleteTask(@PathVariable(value = "id") Long id) {
        taskService.deleteTaskById(id);
        return true;
    }

    //Редактировать выбранный тикет
    @GetMapping("/edit/{id}")
    public TaskEditDto editTask(@PathVariable(value = "id") Long id) {
        return taskService.prepareTaskEditDto(id);
    }

//    @PostMapping("/edit/confirm")
//    public boolean taskEditConfirm(@RequestBody Task task) {
//        ticketService.ticketEditExecute(ticketEdit);
//        return true;
//    }

    @PostMapping("/changeTaskStatus/{id}")
    public boolean changeTaskStatus(@PathVariable(value = "id") Long id,
                                    @RequestParam(value = "status") String status) {
        taskService.changeTaskStatus(id, status);
        return  true;
    }
}
