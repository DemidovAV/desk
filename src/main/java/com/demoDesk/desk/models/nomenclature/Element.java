package com.demoDesk.desk.models.nomenclature;

import com.demoDesk.desk.models.personel.Department;
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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "elementsInProduct")
    private Set<Product> productsWithElement = new HashSet<>();

    public String getDepartmentTitle() {
        return department.getTitle();
    }
}
