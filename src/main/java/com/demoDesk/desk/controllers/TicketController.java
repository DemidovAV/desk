package com.demoDesk.desk.controllers;


import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.ShowTickets;
import com.demoDesk.desk.dto.ticketDto.TicketCreationDto;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import com.demoDesk.desk.services.ProductService;
import com.demoDesk.desk.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;
    private ProductService productService;

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
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

    //создание тикета - отсылаем список изделий
    @GetMapping("/createTicket")
    public List<Product> createNewTicket() {
        return ticketService.getProductsList();
    }

    //создание тикета - после выбора изделия отсылаем список его комплектующих
    @PostMapping("/createTicket/productChosen")
    public List<ProductElementInfo> getElementsForProduct(@RequestBody Product product) {
        Product searchProduct = productService.getProductById(product.getId());
        return productService.getProductElementInfoList(searchProduct);
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
    @GetMapping("/delete/{id}")
    public boolean deleteTicket(@PathVariable(value = "id") Long id) {
        ticketService.deleteTicketById(id);
        return true;
    }

    //Редактировать выбранный тикет
    @GetMapping("/edit/{id}")
    public String editTicket(Model model, @PathVariable(value = "id") Long id) {
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("ticket", ticket);
        return "edit-ticket";
    }

}
