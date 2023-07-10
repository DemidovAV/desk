package com.demoDesk.desk.dto.taskDto;

import com.demoDesk.desk.models.queries.Task;
import lombok.Data;

import java.util.List;

@Data
public class ShowTasksDto {
    private String executorFilter;
    private String elementFilter;
    private List<Task> taskList;
}
