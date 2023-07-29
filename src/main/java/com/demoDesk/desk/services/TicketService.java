package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.ticketDto.TicketCreationDto;
import com.demoDesk.desk.dto.ticketDto.TicketEditDto;
import com.demoDesk.desk.models.enums.RequestStatus;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.personnel.Department;
import com.demoDesk.desk.models.personnel.Employee;
import com.demoDesk.desk.models.queries.Task;
import com.demoDesk.desk.models.queries.Ticket;
import com.demoDesk.desk.repositories.*;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import com.demoDesk.desk.repositories.specifications.TicketSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TaskRepository taskRepository;
	private final ElementRepository elementRepository;
	private final ProductRepository productRepository;
	private final ProductsElementsRepository productsElementsRepository;
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

	public Product getProductById(Long id) {return productRepository.findOne(ProductSpec.findById(id)).orElse(null);}

	private  List<ProductElementInfo> getProductElementInfoList(Product product) {
		List<ProductElementInfo> productElementInfoList = new ArrayList<>();
		Set<Element> elementList = product.getElementsInProduct();
		for(Element e: elementList) {
			productElementInfoList.add(
					new ProductElementInfo(e, productsElementsRepository.getElementQuantityInProduct(product.getId(), e.getId()))
			);
		}
		return productElementInfoList;
	}
	public List<ProductElementInfo> getElementsForProduct(Product product) {
		Product searchProduct = getProductById(product.getId());
		return getProductElementInfoList(searchProduct);
	}

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
		Ticket savedTicket = ticketRepository.save(
				Ticket.builder()
						.creationDate(new Timestamp(System.currentTimeMillis()))
						.comment(creationDto.getComment())
						.priority(creationDto.getPriority())
						.expirationDate(creationDto.getExpirationDate())
						.product(creationDto.getProduct())
						.quantity(creationDto.getQuantity())
						.title(creationDto.getTitle())
						.requestStatus(RequestStatus.IN_PROGRESS.getTitle())
						.build()
		);

		List<ProductElementInfo> elements = creationDto.getProductElementInfos();
		for (ProductElementInfo pei : elements) {
			taskRepository.save(
					Task.builder()
							.creationDate(savedTicket.getCreationDate())
							.ticketId(savedTicket.getId())
							.element(pei.getElement())
							.quantity(pei.getCount())
							.expirationDate(creationDto.getExpirationDate())
							.priority(creationDto.getPriority())
							.executor(getEmployeeForTask(pei.getElement()))
							.build()
			);
		}
	}

	private Employee getEmployeeForTask(Element element) {
		Element searchElement = elementRepository.findById(element.getId()).orElse(null);
		assert searchElement != null;
		Department dep = searchElement.getDepartment();
		List<Employee> employees = dep.getEmployees().stream().filter(e -> e.getStatus().equals("Workable")).collect(Collectors.toList());
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
