package com.demoDesk.desk.dto.ticketDto;

import com.demoDesk.desk.models.queries.Ticket;
import lombok.Data;

import java.util.List;

@Data
public class ShowTicketsDto {
    private List<Ticket> tickets;
    private String filter;
}
