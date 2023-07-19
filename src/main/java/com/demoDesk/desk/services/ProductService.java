package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.productDto.ProductTransferDto;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.nomenclature.ProductsElements;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.ProductRepository;
import com.demoDesk.desk.repositories.ProductsElementsRepository;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsElementsRepository productsElementsRepository;
    private final ElementRepository elementRepository;
    private final ProductRepository productRepository;

    public List<Product> getProductsWithFiltering(Specification<Product> specification) {
        return productRepository.findAll(specification);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

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

    public ProductTransferDto getProductTransferEntity(Product product) {
        ProductTransferDto productTransferDto = new ProductTransferDto();
        productTransferDto.setId(product.getId());
        productTransferDto.setArt(product.getArt());
        productTransferDto.setTitle(product.getTitle());
        productTransferDto.setDescription(product.getDescription());
        productTransferDto.setProductElementInfoList(getProductElementInfoList(product));
        return productTransferDto;
    }
    @Transactional
    public void confirmProductEdit (ProductTransferDto productTransferDto) {
        Product savingProduct = getProductById(productTransferDto.getId());
        savingProduct.setArt(productTransferDto.getArt());
        savingProduct.setTitle(productTransferDto.getTitle());
        savingProduct.setDescription(productTransferDto.getDescription());
        productRepository.save(savingProduct);
        List<ProductElementInfo> productElementInfos = productTransferDto.getProductElementInfoList();
        for(ProductElementInfo pe:productElementInfos) {
            if(savingProduct.getElementsInProduct().contains(pe.getElement())) {
                productsElementsRepository.saveEditedElementQuantityInProduct(
                        savingProduct.getId(),
                        pe.getElement().getId(),
                        pe.getCount());
            } else {
                ProductsElements productsElements = new ProductsElements();
                productsElements.setProductId(savingProduct.getId());
                productsElements.setElementId(pe.getElement().getId());
                productsElements.setElementQuantity(pe.getCount());
                productsElementsRepository.save(productsElements);
            }
        }
    }

    /**
     * ну тут тоже можно объединить в единый метод с confirmProductEdit внимательнее посмотри, код прям идентичен
     * немного логики добавить и все
     *
     */
    @Transactional
    public void saveNewProduct(ProductTransferDto productTransferDto) {
        Product product = new Product();
        product.setArt(productTransferDto.getArt());
        product.setTitle(productTransferDto.getTitle());
        product.setDescription(productTransferDto.getDescription());
        productRepository.save(product);
        List<ProductElementInfo> productElementInfos = productTransferDto.getProductElementInfoList();
        for(ProductElementInfo pei: productElementInfos) {
            ProductsElements productsElements = new ProductsElements();
            productsElements.setProductId(product.getId());
            productsElements.setElementId(pei.getElement().getId());
            productsElements.setElementQuantity(pei.getCount());
            productsElementsRepository.save(productsElements);
        }
    }


}