package com.demoDesk.desk.models.nomenclature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Сущность объединяет изделия и компоненты, хранит информацию о количестве компонентов в конкретном изделии
@Entity
@Table(name = "products_elements")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductsElements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "element_id")
    private Long elementId;

    @Column(name = "element_quantity")
    private Integer elementQuantity;
}
