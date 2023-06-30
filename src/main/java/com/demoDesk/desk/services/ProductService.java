package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.productDto.ProductTransferEntity;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.ProductRepository;
import com.demoDesk.desk.repositories.ProductsElementsRepository;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ProductService {

    private ProductsElementsRepository productsElementsRepository;
    private ElementRepository elementRepository;
    private ProductRepository productRepository;

    @Autowired
    public void setElementRepository(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Autowired
    public void setProductsElementsRepository(ProductsElementsRepository productsElementsRepository) {
        this.productsElementsRepository = productsElementsRepository;
    }

    public List<Product> getProductsWithFiltering(Specification<Product> specification) {
        return productRepository.findAll(specification);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findOne(ProductSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Element> getAllElements() {
        return elementRepository.findAll();
    }

    public List<ProductElementInfo> getProductElementInfoList(Product product) {
        List<ProductElementInfo> productElementInfoList = new ArrayList<>();
        Set<Element> elementList = product.getElementsInProduct();
        for(Element e: elementList) {
            productElementInfoList.add(
                    new ProductElementInfo(e, productsElementsRepository.getElementQuantityInProduct(product.getId(), e.getId()))
            );
        }
        return productElementInfoList;
    }

    public ProductTransferEntity getProductTransferEntity(Product product) {
        ProductTransferEntity productTransferEntity = new ProductTransferEntity();
        productTransferEntity.setId(product.getId());
        productTransferEntity.setArt(product.getArt());
        productTransferEntity.setTitle(product.getTitle());
        productTransferEntity.setDescription(product.getDescription());
        productTransferEntity.setProductElementInfoList(getProductElementInfoList(product));
        return productTransferEntity;
    }



}