package com.demoDesk.desk.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "elements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "art")
    private String art;

    @Column(name = "title")
    private String title;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "elementsInProduct")
    private Set<Product> productsWithElement = new HashSet<>();
}
