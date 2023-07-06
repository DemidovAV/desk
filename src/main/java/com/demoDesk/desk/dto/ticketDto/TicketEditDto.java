package com.demoDesk.desk.dto.ticketDto;

import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import lombok.Data;

import java.util.List;

@Data
public class TicketEditDto {
    private Ticket ticket;
    private List<Task> taskList;
}
