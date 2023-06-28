package com.demoDesk.desk.models.queries;

import com.demoDesk.desk.models.Enums.Priority;
import com.demoDesk.desk.models.Enums.RequestStatus;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.queries.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp creationDate;

    @Column(name = "close_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp closeDate;

    @Column(name = "status")
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "priority")
    private Priority priority;

    @Column(name = "expiration_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp expirationDate;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "ticketId")
    @JsonIgnore
    private List<Task> tasks;

    public String getProductTitle() {
        return product.getTitle();
    }
}