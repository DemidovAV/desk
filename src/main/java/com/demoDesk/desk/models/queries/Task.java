package com.demoDesk.desk.models.queries;

import com.demoDesk.desk.models.Enums.Priority;
import com.demoDesk.desk.models.Enums.RequestStatus;
import com.demoDesk.desk.models.personel.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonIgnore
    private Timestamp expirationDate;

    @Column(name = "priority")
    private Priority priority;

    @Column(name = "status")
    private RequestStatus requestStatus;


    @Column(name = "ticket_id")
    private Long ticketId;

    public String getExecutorName() {
        return executor.getName();
    }



}
