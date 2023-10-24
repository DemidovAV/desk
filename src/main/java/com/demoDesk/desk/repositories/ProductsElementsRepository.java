package com.demoDesk.desk.repositories;

import com.demoDesk.desk.models.nomenclature.ProductsElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
@Repository
public interface ProductsElementsRepository extends JpaRepository<ProductsElements, Long> {
    @Query("SELECT pe.elementQuantity FROM ProductsElements pe WHERE pe.productId=:productId AND pe.elementId=:elementId")
    Integer getElementQuantityInProduct(Long productId, Long elementId);
    @Modifying
    @Query("UPDATE ProductsElements pe SET pe.elementQuantity=:quantity WHERE pe.productId=:productId AND pe.elementId=:elementId")
    void saveEditedElementQuantityInProduct(Long productId, Long elementId, Integer quantity);


}
