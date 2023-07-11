package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.TicketCreationDto;
import com.demoDesk.desk.dto.ticketDto.TicketEditDto;
import com.demoDesk.desk.models.Enums.RequestStatus;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.nomenclature.ProductsElements;
import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.queries.Task;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    public List<Product> getProductsList(){return productRepository.findAll();}

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

    public void createNewTicket(TicketCreationDto creationDto) {
        Ticket newTicket = new Ticket();
        newTicket.setCreationDate(new Timestamp(System.currentTimeMillis()));
        newTicket.setComment(creationDto.getComment());
        newTicket.setPriority(creationDto.getPriority());
        newTicket.setExpirationDate(creationDto.getExpirationDate());
        newTicket.setProduct(creationDto.getProduct());
        newTicket.setQuantity(creationDto.getQuantity());
        newTicket.setTitle(creationDto.getTitle());
        newTicket.setRequestStatus(RequestStatus.IN_PROGRESS);
        ticketRepository.save(newTicket);

        Ticket savedTicket = ticketRepository.findFirstByOrderByIdDesc();

        List<ProductElementInfo> elements = creationDto.getProductElementInfos();
        for(ProductElementInfo pei: elements) {
            Task task = new Task();
            task.setCreationDate(new Timestamp(System.currentTimeMillis()));
            task.setTicketId(savedTicket.getId());
            task.setElement(pei.getElement());
            task.setQuantity(pei.getCount());
            task.setExpirationDate(creationDto.getExpirationDate());
            task.setPriority(creationDto.getPriority());
            task.setExecutor(getEmployeeForTask(pei.getElement()));
            taskRepository.save(task);

        }
    }

    private Employee getEmployeeForTask(Element element) {
        Element searchElement = elementRepository.findById(element.getId()).orElse(null);
        assert searchElement != null;
        Department dep = searchElement.getDepartment();
        List<Employee> employees = dep.getEmployees();
        return employees.stream().min(Comparator.comparingInt(e -> e.getTasks().size())).orElseGet(null);
    }

    public void ticketEditExecute(TicketEditDto ticketEdit) {
        Ticket ticket = findById(ticketEdit.getTicket().getId());
        ticket.setTitle(ticketEdit.getTicket().getTitle());
        ticket.setProduct(ticketEdit.getTicket().getProduct());
        ticket.setQuantity(ticketEdit.getTicket().getQuantity());
        ticket.setPriority(ticketEdit.getTicket().getPriority());
        ticket.setExpirationDate(ticketEdit.getTicket().getExpirationDate());
        ticket.setComment(ticketEdit.getTicket().getComment());
        ticket.setRequestStatus(ticketEdit.getTicket().getRequestStatus());
        ticketRepository.save(ticket);
    }

}
