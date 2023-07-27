package com.demoDesk.desk.models.nomenclature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Изделие, состоит из компонентов
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    public void removeElement(Element element) {
        this.elementsInProduct.remove(element);
        element.getProductsWithElement().remove(this);
    }

}
