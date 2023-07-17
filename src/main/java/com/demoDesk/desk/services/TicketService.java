package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.TicketCreationDto;
import com.demoDesk.desk.dto.ticketDto.TicketEditDto;
import com.demoDesk.desk.models.enums.RequestStatus;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.models.personel.Employee;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.ProductRepository;
import com.demoDesk.desk.repositories.TaskRepository;
import com.demoDesk.desk.repositories.TicketRepository;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TaskRepository taskRepository;
	private final ElementRepository elementRepository;
	private final ProductRepository productRepository;
	private final TicketRepository ticketRepository;


	public List<Ticket> getTicketsWithFiltering(Specification<Ticket> specification) {
		return ticketRepository.findAll(specification);
	}

	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	public Ticket findById(Long id) {
		return ticketRepository.findOne(TicketSpec.findById(id)).orElse(null);
	}

	@Transactional
	public void deleteTicketById(Long id) {
		ticketRepository.deleteById(id);
	}


	public List<Product> getProductsList() {
		return productRepository.findAll();
	}


	/**
	 * Продолжение примера, тут мы используем switch. и отсутствует почти везде дублирующий код(пример не весь,
	 * остальные сортировки можно добавить)
	 */
	public List<Ticket> sortTickets(List<Ticket> tickets, String sortParameter) {
		switch (sortParameter) {
			case "expirationDate": {
				return tickets.stream().sorted(Comparator.comparing(Ticket::getExpirationDate)).collect(Collectors.toList());
			}
			case "closeDate": {
				return tickets.stream().sorted(Comparator.comparing(Ticket::getCloseDate)).collect(Collectors.toList());
			}
			case "creationDate": {
				return tickets.stream().sorted(Comparator.comparing(Ticket::getCreationDate)).collect(Collectors.toList());
			}
			case "status": {
				return tickets.stream().sorted(Comparator.comparing(Ticket::getRequestStatus)).collect(Collectors.toList());

			}
			case "priority": {
				return tickets.stream().sorted(Comparator.comparing(Ticket::getPriority)).collect(Collectors.toList());

			}
			default:
				return tickets;
		}

	}


	@Transactional
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
	@Transactional
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
