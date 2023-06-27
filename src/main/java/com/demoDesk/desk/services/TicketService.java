package com.demoDesk.desk.services;

import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.ProductRepository;
import com.demoDesk.desk.repositories.TaskRepository;
import com.demoDesk.desk.repositories.TicketRepository;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private TaskRepository taskRepository;
    private ElementRepository elementRepository;
    private ProductRepository productRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {this.taskRepository = taskRepository;}

    @Autowired
    public void setElementRepository(ElementRepository elementRepository) {this.elementRepository = elementRepository;}

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {this.productRepository = productRepository;}

    public List<Ticket> getTicketsWithFiltering(Specification<Ticket> specification) {
        return ticketRepository.findAll(specification);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ticket findById(Long id) {
        return ticketRepository.findOne(TicketSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

    @Transactional
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> sortTicketsByCreationDate (List<Ticket> tickets){
        return tickets.stream().sorted(Comparator.comparing(Ticket::getCreationDate)).collect(Collectors.toList());
    }

    public List<Ticket> sortTicketsByCloseDate (List<Ticket> tickets){
        return tickets.stream().sorted(Comparator.comparing(Ticket::getCloseDate)).collect(Collectors.toList());
    }

    public List<Ticket> sortTicketsByExpirationDate (List<Ticket> tickets){
        return tickets.stream().sorted(Comparator.comparing(Ticket::getExpirationDate)).collect(Collectors.toList());
    }

    public List<Ticket> sortTicketsByStatus (List<Ticket> tickets){
        return tickets.stream().sorted(Comparator.comparing(Ticket::getRequestStatus)).collect(Collectors.toList());
    }

    public List<Ticket> sortTicketsByPriority (List<Ticket> tickets){
        return tickets.stream().sorted(Comparator.comparing(Ticket::getPriority)).collect(Collectors.toList());
    }

}
