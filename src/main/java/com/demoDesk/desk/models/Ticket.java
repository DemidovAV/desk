package com.demoDesk.desk.models;

import com.demoDesk.desk.models.Enums.Priority;
import com.demoDesk.desk.models.Enums.RequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "close_date")
    private Timestamp closeDate;

    @Column(name = "status")
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "priority")
    private Priority priority;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "ticket")
    private List<Task> tasks;
}
