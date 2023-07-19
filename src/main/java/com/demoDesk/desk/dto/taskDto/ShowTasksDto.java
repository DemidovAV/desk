package com.demoDesk.desk.dto.taskDto;

import com.demoDesk.desk.models.queries.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowTasksDto {
    private String executorFilter;
    private String elementFilter;
    private List<Task> tasksList;
}
