package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.taskDto.ShowTasksDto;
import com.demoDesk.desk.dto.taskDto.TaskEditDto;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.repositories.specifications.TaskSpec;
import com.demoDesk.desk.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

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
        showTasksDto.setTaskList(taskService.getTasksWithFiltering(spec(executor, element)));
        showTasksDto.setExecutorFilter(executor);
        showTasksDto.setElementFilter(element);
        return showTasksDto;
    }

    //Сортируем отфильтрованные таски по дате дедлайна
    @PostMapping("/sortByExpirationDate")
    public ShowTasksDto sortByExpirationDate(@RequestParam(value = "executor", required = false) String executor,
                                             @RequestParam(value = "element", required = false) String element){
        ShowTasksDto showTasksDto = new ShowTasksDto();
        List<Task> filteredTasks = taskService.getTasksWithFiltering(spec(executor, element));
        List<Task> sortedTasks = taskService.sortTasksByExpirationDate(filteredTasks);
        showTasksDto.setTaskList(sortedTasks);
        showTasksDto.setExecutorFilter(executor);
        showTasksDto.setElementFilter(element);
        return showTasksDto;
    }

    //Сортируем отфильтрованные таски по дате создания
    @PostMapping("/sortByCreationDate")
    public ShowTasksDto sortByCreationDate(@RequestParam(value = "executor", required = false) String executor,
                                           @RequestParam(value = "element", required = false) String element){
        ShowTasksDto showTasksDto = new ShowTasksDto();
        List<Task> filteredTasks = taskService.getTasksWithFiltering(spec(executor, element));
        List<Task> sortedTasks = taskService.sortTasksByCreationDate(filteredTasks);
        showTasksDto.setTaskList(sortedTasks);
        showTasksDto.setExecutorFilter(executor);
        showTasksDto.setElementFilter(element);
        return showTasksDto;
    }

    //Сортируем отфильтрованные таски по дате закрытия тикета
    @PostMapping("/sortByCloseDate")
    public ShowTasksDto sortByCloseDate(@RequestParam(value = "executor", required = false) String executor,
                                           @RequestParam(value = "element", required = false) String element){
        ShowTasksDto showTasksDto = new ShowTasksDto();
        List<Task> filteredTasks = taskService.getTasksWithFiltering(spec(executor, element));
        List<Task> sortedTasks = taskService.sortTasksByCloseDate(filteredTasks);
        showTasksDto.setTaskList(sortedTasks);
        showTasksDto.setExecutorFilter(executor);
        showTasksDto.setElementFilter(element);
        return showTasksDto;
    }

    //Сортируем отфильтрованные таски по статусу
    @PostMapping("/sortByStatus")
    public ShowTasksDto sortByStatus(@RequestParam(value = "executor", required = false) String executor,
                                        @RequestParam(value = "element", required = false) String element){
        ShowTasksDto showTasksDto = new ShowTasksDto();
        List<Task> filteredTasks = taskService.getTasksWithFiltering(spec(executor, element));
        List<Task> sortedTasks = taskService.sortTasksByStatus(filteredTasks);
        showTasksDto.setTaskList(sortedTasks);
        showTasksDto.setExecutorFilter(executor);
        showTasksDto.setElementFilter(element);
        return showTasksDto;
    }

    //Сортируем отфильтрованные таски по приоритету
    @PostMapping("/sortByPriority")
    public ShowTasksDto sortByPriority(@RequestParam(value = "executor", required = false) String executor,
                                        @RequestParam(value = "element", required = false) String element){
        ShowTasksDto showTasksDto = new ShowTasksDto();
        List<Task> filteredTasks = taskService.getTasksWithFiltering(spec(executor, element));
        List<Task> sortedTasks = taskService.sortTasksByPriority(filteredTasks);
        showTasksDto.setTaskList(sortedTasks);
        showTasksDto.setExecutorFilter(executor);
        showTasksDto.setElementFilter(element);
        return showTasksDto;
    }

    //Сброс фильтров
    @PostMapping("/reset")
    public ShowTasksDto resetShowTickets() {
        ShowTasksDto showTasksDto = new ShowTasksDto();
        showTasksDto.setTaskList(taskService.getAllTasks());
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
}
