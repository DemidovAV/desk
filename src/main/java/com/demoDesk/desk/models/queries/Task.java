package com.demoDesk.desk.models.queries;

import com.demoDesk.desk.models.enums.Priority;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personnel.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

//Таск, автоматически формируется при создании тикета. В дальнейшем есть возможность редактирования
@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee executor;

    @Column(name = "expiration_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp expirationDate;

    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp creationDate;

    @Column(name = "close_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp closeDate;

    @Column(name = "priority")
    private Priority priority;

    @Column(name = "status")
    private String requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Element element;

    @Column(name = "element_quantity")
    private Integer quantity;

    @Column(name = "ticket_id")
    private Long ticketId;


}
