package com.demoDesk.desk.controllers;


import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.ShowTicketsDto;
import com.demoDesk.desk.dto.ticketDto.TicketCreationDto;
import com.demoDesk.desk.dto.ticketDto.TicketEditDto;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import com.demoDesk.desk.services.ProductService;
import com.demoDesk.desk.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    public ShowTicketsDto showTickets(@RequestParam(value = "filter", required = false) String filter){
        ShowTicketsDto showTicketsDto = new ShowTicketsDto();
        showTicketsDto.setTickets(ticketService.getTicketsWithFiltering(spec(filter)));
        showTicketsDto.setFilter(filter);
        return showTicketsDto;
    }

    //Сортируем отфильтрованные тикеты по дате дедлайна
    @PostMapping("/sortByExpirationDate")
    public ShowTicketsDto sortByExpirationDate(@RequestParam(value = "filter", required = false) String filter){
        ShowTicketsDto showTicketsDto = new ShowTicketsDto();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByExpirationDate(filteredTickets);
        showTicketsDto.setTickets(sortedTickets);
        showTicketsDto.setFilter(filter);
        return showTicketsDto;
    }

    //Сортируем отфильтрованные тикеты по дате создания
    @PostMapping("/sortByCreationDate")
    public ShowTicketsDto sortByCreationDate(@RequestParam(value = "filter", required = false) String filter){
        ShowTicketsDto showTicketsDto = new ShowTicketsDto();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByCreationDate(filteredTickets);
        showTicketsDto.setTickets(sortedTickets);
        showTicketsDto.setFilter(filter);
        return showTicketsDto;
    }

    //Сортируем отфильтрованные тикеты по дате закрытия тикета
    @PostMapping("/sortByCloseDate")
    public ShowTicketsDto sortByCloseDate(@RequestParam(value = "filter", required = false) String filter){
        ShowTicketsDto showTicketsDto = new ShowTicketsDto();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByCloseDate(filteredTickets);
        showTicketsDto.setTickets(sortedTickets);
        showTicketsDto.setFilter(filter);
        return showTicketsDto;
    }

    //Сортируем отфильтрованные тикеты по статусу
    @PostMapping("/sortByStatus")
    public ShowTicketsDto sortByStatus(@RequestParam(value = "filter", required = false) String filter){
        ShowTicketsDto showTicketsDto = new ShowTicketsDto();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByStatus(filteredTickets);
        showTicketsDto.setTickets(sortedTickets);
        showTicketsDto.setFilter(filter);
        return showTicketsDto;
    }

    //Сортируем отфильтрованные тикеты по приоритету
    @PostMapping("/sortByPriority")
    public ShowTicketsDto sortByPriority(@RequestParam(value = "filter", required = false) String filter){
        ShowTicketsDto showTicketsDto = new ShowTicketsDto();
        List<Ticket> filteredTickets = ticketService.getTicketsWithFiltering(spec(filter));
        List<Ticket> sortedTickets = ticketService.sortTicketsByPriority(filteredTickets);
        showTicketsDto.setTickets(sortedTickets);
        showTicketsDto.setFilter(filter);
        return showTicketsDto;
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
    @DeleteMapping ("/delete/{id}")
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
