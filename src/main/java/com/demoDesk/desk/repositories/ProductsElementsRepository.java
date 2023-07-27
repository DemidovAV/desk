package com.demoDesk.desk.repositories;

import com.demoDesk.desk.models.nomenclature.ProductsElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedNativeQuery;

public interface ProductsElementsRepository extends JpaRepository<ProductsElements, Long> {
    @Query("SELECT pe.elementQuantity FROM ProductsElements pe WHERE pe.productId=:productId AND pe.elementId=:elementId")
    Integer getElementQuantityInProduct(Long productId, Long elementId);
    @Modifying
    @Query("UPDATE ProductsElements pe SET pe.elementQuantity=:quantity WHERE pe.productId=:productId AND pe.elementId=:elementId")
    void saveEditedElementQuantityInProduct(Long productId, Long elementId, Integer quantity);


}
