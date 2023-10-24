package com.demoDesk.desk.controllers;


import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.ShowTicketsDto;
import com.demoDesk.desk.dto.ticketDto.TicketCreationDto;
import com.demoDesk.desk.dto.ticketDto.TicketEditDto;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import com.demoDesk.desk.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;


    private Specification<Ticket> spec(String filter) {
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        return spec;
    }

    //Показывает страницу со списком уже существующих тикетов
    @GetMapping
    public ShowTicketsDto showTickets(@RequestParam(value = "filter", required = false) String filter){
        return ShowTicketsDto.builder()
            .tickets(ticketService.getTicketsWithFiltering(spec(filter))) // много кода, проще сделать так
            .filter(filter)
            .build();
    }

    //Сортируем отфильтрованные тикеты по дате дедлайна
    @PostMapping
    public ShowTicketsDto sortTickets(@RequestParam(value = "filter", required = false) String filter,  @RequestParam("sort") String sortBy){
        List<Ticket> filteredTickets= ticketService.getTicketsWithFiltering(spec(filter));

        return   ShowTicketsDto.builder()
            .tickets(ticketService.sortTickets(filteredTickets, sortBy)) // много кода, проще сделать так
            .filter(filter)
            .build();
    }

    //Сброс фильтров
    @PostMapping("/reset")
    public ShowTicketsDto resetShowTickets() {
        ShowTicketsDto showTicketsDto = new ShowTicketsDto();
        showTicketsDto.setTickets(ticketService.getAllTickets());
        showTicketsDto.setFilter(null);
        return showTicketsDto;
    }

    //создание тикета - отсылаем список изделий
    @GetMapping("/createTicket")
    public List<Product> createNewTicket() {
        return ticketService.getProductsList();
    }

    //создание тикета - после выбора изделия отсылаем список его комплектующих
    @PostMapping("/createTicket/productChosen")
    public List<ProductElementInfo> getElementsForProduct(@RequestBody Product product) {
        return ticketService.getElementsForProduct(product);
    }

    //Подтверждение создания тикета, сохраняем новый тикет в базе
    @PostMapping("/createTicket/confirm")
    public boolean createConfirm(@RequestBody TicketCreationDto creationDto) {
        ticketService.createNewTicket(creationDto);
        return true;
    }

    //Просмотр отдельного тикета
    @GetMapping("/showTicket/{id}")
    public Ticket showTicket (@PathVariable(value = "id") Long id) {
        return ticketService.findById(id);
    }

    //Удалить выбранный тикет
    @DeleteMapping ("/deleteTicket/{id}")
    public boolean deleteTicket(@PathVariable(value = "id") Long id) {
        ticketService.deleteTicketById(id);
        return true;
    }

    //Редактировать выбранный тикет
    @GetMapping("/edit/{id}")
    public TicketEditDto editTicket(@PathVariable(value = "id") Long id) {
        Ticket ticket = ticketService.findById(id);
        List<Task> tasks = ticket.getTasks();
        TicketEditDto ticketEdit = new TicketEditDto();
        ticketEdit.setTicket(ticket);
        ticketEdit.setTaskList(tasks);
        return ticketEdit;
    }


    @PostMapping("/edit/confirm")
    public boolean ticketEditConfirm(@RequestBody TicketEditDto ticketEdit) {
        ticketService.ticketEditExecute(ticketEdit);
        return true;
    }

}
