package com.demoDesk.desk.models.nomenclature;

import com.demoDesk.desk.models.personel.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "elementsInProduct", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Product> productsWithElement = new HashSet<>();

    public String getDepartmentTitle() {
        return department.getTitle();
    }
}
