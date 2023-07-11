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
    /**
     * я тут не понял, зачем ты возвращаешь фильтр. получанется, ты отфильтровал по тому
     * шаблону, который к тебе пришел, сформулировал список и отправил его обратно, так зачем фильтр передавать?
     * я бы его удалил, сам фильтр хранится на том фронте, который тебе отправил запрос, он его отобразит
     */
    private String filter;

}
