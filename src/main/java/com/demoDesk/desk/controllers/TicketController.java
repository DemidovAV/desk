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
        return ShowTicketsDto.builder()
            .tickets(ticketService.getTicketsWithFiltering(spec(filter))) // много кода, проще сделать так
            .filter(filter)
            .build();
    }

    //Сортируем отфильтрованные тикеты по дате дедлайна

    /**
     * Почему у тебя фильтр стоит  required = false, у тебя запрос может быть без поля  этого?
     *
     * Так, смотри, далее ниже у тебя идет громадный код, который на 100% идентичный. Тут можно создать одну точку
     * входа, которая будет проводить фильтровку и сортировкую для примера: добавляем еще один параметр при входе,
     * название поля.В такой реализации не нужно дублировать кучу кода.
     * еще строки для фильтров можно передавать списком, глянь код https://habr.com/ru/companies/otus/articles/707724/
     *
     */
    @PostMapping
    public ShowTicketsDto sortTickets(@RequestParam(value = "filter", required = false) String filter,  @RequestParam("sort") String sortBy){
        List<Ticket> filteredTickets= ticketService.getTicketsWithFiltering(spec(filter));

        return   ShowTicketsDto.builder()
            .tickets(ticketService.sortTickets(filteredTickets, sortBy)) // много кода, проще сделать так
            .filter(filter)
            .build();
    }


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

    /**
     * ПОЧЕМУ в контроллере тикета у тебя висят продуктовые точки входа?
     * и я до конца не осознал смысл этих точек входа
     */
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

    /**
     * Вот это лишнее. Смотри, на фронте данные о тикете хранятся. он может эти данные добавить в кэш браузера и
     * вытягивать эти данные. т.е. открывает страницу новую и в ней отображает инфу того или иного тикета.
     *
     *
     */
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

    /**
     * Это тоже лишнее, ты можешь использовать для этой операции код контроллера  @PostMapping("/createTicket/confirm")
     * пример записи новой сущности или редактирования существующей я тебе кидал, если не понял как применить,
     * отпишись, сделаю.
     */
    @PostMapping("/edit/confirm")
    public boolean ticketEditConfirm(@RequestBody TicketEditDto ticketEdit) {
        ticketService.ticketEditExecute(ticketEdit);
        return true;
    }

}
