package com.demoDesk.desk.models;

import com.demoDesk.desk.models.Enums.Priority;
import com.demoDesk.desk.models.Enums.RequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Employee executor;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @Column(name = "priority")
    private Priority priority;

    @Column(name = "status")
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
