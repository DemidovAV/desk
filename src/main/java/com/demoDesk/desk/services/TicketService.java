package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.*;
import com.demoDesk.desk.models.enums.RequestStatus;
import com.demoDesk.desk.models.nomenclature.*;
import com.demoDesk.desk.models.personel.*;
import com.demoDesk.desk.models.queries.*;
import com.demoDesk.desk.repositories.*;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
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
	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Autowired
	public void setElementRepository(ElementRepository elementRepository) {
		this.elementRepository = elementRepository;
	}

	@Autowired
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

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

	public List<Product> getProductsList() {
		return productRepository.findAll();
	}

	public List<Ticket> sortTicketsByCreationDate(List<Ticket> tickets) {
		return tickets.stream().sorted(Comparator.comparing(Ticket::getCreationDate)).collect(Collectors.toList());
	}

	public List<Ticket> sortTicketsByCloseDate(List<Ticket> tickets) {
		return tickets.stream().sorted(Comparator.comparing(Ticket::getCloseDate)).collect(Collectors.toList());
	}

	public List<Ticket> sortTicketsByExpirationDate(List<Ticket> tickets) {
		return tickets.stream().sorted(Comparator.comparing(Ticket::getExpirationDate)).collect(Collectors.toList());
	}

	/**
	 * Продолжение примера, тут мы используем switch. и отсутствует почти везде дублирующий код(пример не весь,
	 * остальные сортировки можно добавить)
	 */
	public List<Ticket> sortTicketsExample(List<Ticket> tickets, String fieldName) {
		switch (fieldName) {
			case "expirationDate": {
				return tickets.stream().sorted(Comparator.comparing(Ticket::getExpirationDate)).collect(Collectors.toList());
			}
			case "status": {
				return tickets.stream().sorted(Comparator.comparing(Ticket::getRequestStatus)).collect(Collectors.toList());

			}
			default:
				return tickets;
		}

	}

	public List<Ticket> sortTicketsByStatus(List<Ticket> tickets) {
		return tickets.stream().sorted(Comparator.comparing(Ticket::getRequestStatus)).collect(Collectors.toList());
	}

	public List<Ticket> sortTicketsByPriority(List<Ticket> tickets) {
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
		for (ProductElementInfo pei : elements) {
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
