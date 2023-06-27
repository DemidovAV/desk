package com.demoDesk.desk.controllers;


import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import com.demoDesk.desk.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    //Показывает страницу со списком уже существующих тикетов
    @GetMapping
    public String showTickets(Model model,
                              @RequestParam(value = "filter", required = false) String filter){
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec);
        model.addAttribute("tickets", filteredTickets);
        model.addAttribute("filter", filter);
        return "tickets";
    }

    //Сортируем отфильтрованные тикеты по дате дедлайна
    @PostMapping("/sortByExpirationDate")
    public String sortByExpirationDate(Model model,
                                       @RequestParam(value = "filter", required = false) String filter){
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec);
        List<Ticket> sortedTickets = ticketService.sortTicketsByExpirationDate(filteredTickets);
        model.addAttribute("tickets", sortedTickets);
        model.addAttribute("filter", filter);
        return "tickets";
    }

    //Сортируем отфильтрованные тикеты по дате создания
    @PostMapping("/sortByCreationDate")
    public String sortByCreationDate(Model model,
                                       @RequestParam(value = "filter", required = false) String filter){
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec);
        List<Ticket> sortedTickets = ticketService.sortTicketsByCreationDate(filteredTickets);
        model.addAttribute("tickets", sortedTickets);
        model.addAttribute("filter", filter);
        return "tickets";
    }

    //Сортируем отфильтрованные тикеты по дате закрытия тикета
    @PostMapping("/sortByCloseDate")
    public String sortByCloseDate(Model model,
                                     @RequestParam(value = "filter", required = false) String filter){
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec);
        List<Ticket> sortedTickets = ticketService.sortTicketsByCloseDate(filteredTickets);
        model.addAttribute("tickets", sortedTickets);
        model.addAttribute("filter", filter);
        return "tickets";
    }

    //Сортируем отфильтрованные тикеты по статусу
    @PostMapping("/sortByStatus")
    public String sortByStatus(Model model,
                                  @RequestParam(value = "filter", required = false) String filter){
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec);
        List<Ticket> sortedTickets = ticketService.sortTicketsByStatus(filteredTickets);
        model.addAttribute("tickets", sortedTickets);
        model.addAttribute("filter", filter);
        return "tickets";
    }

    //Сортируем отфильтрованные тикеты по приоритету
    @PostMapping("/sortByPriority")
    public String sortByPriority(Model model,
                                  @RequestParam(value = "filter", required = false) String filter){
        Ticket ticket = new Ticket();
        Specification<Ticket> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(TicketSpec.titleContains(filter));
        }
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec);
        List<Ticket> sortedTickets = ticketService.sortTicketsByPriority(filteredTickets);
        model.addAttribute("ticket", ticket);
        model.addAttribute("tickets", sortedTickets);
        model.addAttribute("filter", filter);
        return "tickets";
    }

    //Сброс фильтров
    @PostMapping("/reset")
    public String resetShowTickets(Model model) {
        Ticket ticket = new Ticket();
        String filter = null;
        model.addAttribute("tickets", ticketService.getAllTickets());
        model.addAttribute("ticket", ticket);
        model.addAttribute("filter", filter);
        return "redirect:/tickets";
    }

    //Создание нового тикета
    @PostMapping("/createTicket")
    public boolean createNewTicket(@RequestBody Ticket ticket) {
        ticketService.saveTicket(ticket);
        return true;
    }

    @GetMapping("/createTicket")
    public ResponseCreareTicket createNewTicket() {
        ticketService.saveTicket(ticket);
        return ;
    }
    @GetMapping("/productElementInfo")
    public List<ProductElementInfo> createNewTicket(@RequestParam("productId") Long productId {


    }

    //Подтверждение создания тикета
    @PostMapping("/createTicket/confirm")
    public String createConfirm(@ModelAttribute(value="ticket") Ticket ticket) {
        ticketService.saveTicket(ticket);
        return "created-ticket";
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
