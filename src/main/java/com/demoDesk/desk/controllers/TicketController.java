package com.demoDesk.desk.controllers;


import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.ResponseCreateTicket;
import com.demoDesk.desk.dto.ticketDto.ShowTickets;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import com.demoDesk.desk.services.ProductService;
import com.demoDesk.desk.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    private Specification<Ticket> spec(String filter) {
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        return spec;
    }

    //Показывает страницу со списком уже существующих тикетов
    @GetMapping
    public ShowTickets showTickets(@RequestParam(value = "filter", required = false) String filter){
        ShowTickets showTickets = new ShowTickets();
        showTickets.setTickets(ticketService.getTicketsWithFiltering(spec(filter)));
        showTickets.setFilter(filter);
        return showTickets;
    }

    //Сортируем отфильтрованные тикеты по дате дедлайна
    @PostMapping("/sortByExpirationDate")
    public ShowTickets sortByExpirationDate(@RequestParam(value = "filter", required = false) String filter){
        ShowTickets showTickets = new ShowTickets();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByExpirationDate(filteredTickets);
        showTickets.setTickets(sortedTickets);
        showTickets.setFilter(filter);
        return showTickets;
    }

    //Сортируем отфильтрованные тикеты по дате создания
    @PostMapping("/sortByCreationDate")
    public ShowTickets sortByCreationDate(@RequestParam(value = "filter", required = false) String filter){
        ShowTickets showTickets = new ShowTickets();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByCreationDate(filteredTickets);
        showTickets.setTickets(sortedTickets);
        showTickets.setFilter(filter);
        return showTickets;
    }

    //Сортируем отфильтрованные тикеты по дате закрытия тикета
    @PostMapping("/sortByCloseDate")
    public ShowTickets sortByCloseDate(@RequestParam(value = "filter", required = false) String filter){
        ShowTickets showTickets = new ShowTickets();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByCloseDate(filteredTickets);
        showTickets.setTickets(sortedTickets);
        showTickets.setFilter(filter);
        return showTickets;
    }

    //Сортируем отфильтрованные тикеты по статусу
    @PostMapping("/sortByStatus")
    public ShowTickets sortByStatus(@RequestParam(value = "filter", required = false) String filter){
        ShowTickets showTickets = new ShowTickets();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByStatus(filteredTickets);
        showTickets.setTickets(sortedTickets);
        showTickets.setFilter(filter);
        return showTickets;
    }

    //Сортируем отфильтрованные тикеты по приоритету
    @PostMapping("/sortByPriority")
    public ShowTickets sortByPriority(@RequestParam(value = "filter", required = false) String filter){
        ShowTickets showTickets = new ShowTickets();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByPriority(filteredTickets);
        showTickets.setTickets(sortedTickets);
        showTickets.setFilter(filter);
        return showTickets;
    }

    //Сброс фильтров
    @PostMapping("/reset")
    public ShowTickets resetShowTickets(Model model) {
        ShowTickets showTickets = new ShowTickets();
        showTickets.setTickets(ticketService.getAllTickets());
        showTickets.setFilter(null);
        return showTickets;
    }

    //Создание нового тикета
//    @PostMapping("/createTicket/confirm")
//    public boolean createNewTicket(@RequestBody Ticket ticket) {
//        ticketService.saveTicket(ticket);
//        return true;
//    }

    @GetMapping("/createTicket")
    public List<Product> createNewTicket() {
        return ticketService.getProductsList();
    }

//    public ResponseCreateTicket responseCreateTicket()
//    @GetMapping("/productElementInfo")
//    public List<ProductElementInfo> createNewTicket(@RequestParam("productId") Long productId {
//
//
//    }

    //Подтверждение создания тикета
    @PostMapping("/createTicket/confirm")
    public boolean createConfirm(@ModelAttribute(value="ticket") Ticket ticket) {
        ticketService.saveTicket(ticket);
        return true;
    }

    //Просмотр отдельного тикета
    @GetMapping("/show/{id}")
    public String showTicket(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("ticket", ticketService.findById(id));
        return "show-ticket";
    }

    //Удалить выбранный тикет
    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable(value = "id") Long id) {
        ticketService.deleteTicketById(id);
        return "redirect:/tickets";
    }

    //Редактировать выбранный тикет
    @GetMapping("/edit/{id}")
    public String editTicket(Model model, @PathVariable(value = "id") Long id) {
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("ticket", ticket);
        return "edit-ticket";
    }

}
