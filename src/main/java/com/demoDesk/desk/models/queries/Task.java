package com.demoDesk.desk.models.queries;

import com.demoDesk.desk.models.Enums.Priority;
import com.demoDesk.desk.models.Enums.RequestStatus;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personel.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
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
    private RequestStatus requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Element element;

    @Column(name = "element_quantity")
    private Integer quantity;

    @Column(name = "ticket_id")
    private Long ticketId;


}
