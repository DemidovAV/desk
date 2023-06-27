package com.demoDesk.desk.models.nomenclature;

import com.demoDesk.desk.models.nomenclature.Element;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "art")
    private String art;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "products_elements",
        joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "element_id", referencedColumnName = "id")
        }
    )
    @JsonIgnore
    private Set<Element> elementsInProduct = new HashSet<>();

}
