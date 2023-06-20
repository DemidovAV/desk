package com.demoDesk.desk.controllers;

import com.demoDesk.desk.models.Ticket;
import com.demoDesk.desk.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String showTickets(Model model,
                              @RequestParam(value = "filter", required = false) String filter,
                              @RequestParam(value = "min", required = false) Integer min,
                              @RequestParam(value = "max", required = false) Integer max){

        return "tickets";
    }

    @PostMapping("/reset")
    public String resetShowTickets(Model model) {

        return "redirect:/tickets";
    }

    @GetMapping("/create")
    public String createNewTicket(Model model) {

    }

    @GetMapping("/show/{id}")
    public String showTicket(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("ticket", ticketService.findById(id));
        return "show-ticket";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable(value = "id") Long id) {
        ticketService.deleteTicketById(id);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(Model model, @PathVariable(value = "id") Long id) {
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("ticket", ticket);
        return "product-edit";
    }
}
