package com.demoDesk.desk.dto.ticketDto;

import com.demoDesk.desk.models.queries.Ticket;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowTicketsDto {
    private List<Ticket> tickets;
    private String filter;

}
