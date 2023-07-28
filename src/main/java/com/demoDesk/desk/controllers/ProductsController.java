package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.elementDto.AddElementToProductDto;
import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.dto.productDto.ProductTransferDto;
import com.demoDesk.desk.dto.productDto.ShowProductsDto;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import com.demoDesk.desk.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    private Specification<Product> filtration(String filter, String art) {
        Specification<Product> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(ProductSpec.titleContains(filter));
        }
        if (art != null) {
            spec = spec.and(ProductSpec.artContains(art));
        }
        return spec;

    }

    @GetMapping
    public ShowProductsDto showProducts(@RequestParam(value = "filter", required = false) String filter,
                                        @RequestParam(value = "art", required = false) String art) {
        ShowProductsDto showProductsDto = new ShowProductsDto();
        showProductsDto.setProducts(productService.getProductsWithFiltering(filtration(filter, art)));
        showProductsDto.setFilter(filter);
        showProductsDto.setArt(art);

        return showProductsDto;
    }

    @PostMapping("/reset")
    public ShowProductsDto showProductListReset() {
        ShowProductsDto showProductsDto = new ShowProductsDto();
        showProductsDto.setProducts(productService.getAllProducts());
        showProductsDto.setFilter(null);
        showProductsDto.setFilter(null);
        return showProductsDto;
    }

    @GetMapping("/showOrEditProduct/{id}")
    public ProductTransferDto showOrEditProduct(@PathVariable(value="id") Long id) {
        Product product = productService.getProductById(id);
        return productService.getProductTransferDto(product);
    }

    @PostMapping("/saveProduct")
    public ProductTransferDto saveNewOrEditedProduct(@RequestBody ProductTransferDto productTransferDto) {
        return productService.addOrEditProductConfirm(productTransferDto);
    }

    @GetMapping("/showElementsInProduct/{id}")
    public List<ProductElementInfo> getElementsInProduct (@PathVariable(value="id") Long id) {
       return productService.getProductElementInfoList(productService.getProductById(id));
    }

    @PostMapping("/showElementsInProduct/deleteElement")
    public boolean deleteElementFromProduct(@RequestParam(value = "productId") Long productId,
                                            @RequestParam(value = "elementId") Long elementId) {
        productService.deleteElementFromProduct(productId, elementId);
        return true;
    }

    @PostMapping("/showElementsInProduct/changeElementQuantity")
    public boolean changeElementQuantityInProduct(@RequestParam(value = "productId") Long productId,
                                                  @RequestParam(value = "elementId") Long elementId,
                                                  @RequestParam(value = "quantity") Integer quantity){
        productService.changeElementQuantityInProduct(productId, elementId, quantity);
        return true;
    }

    @GetMapping("/showElementsInProduct/addExistingElement")
    public List<Element> showElementsToAdd() {
        return productService.getAllElements();
    }

    @PostMapping("/showElementsInProduct/addElementToProduct/{id}")
    public boolean addExistingElementToProduct(@PathVariable(value = "id") Long productId,
                                               @RequestBody AddElementToProductDto addElementToProductDto) {
        productService.addElementToProduct(productId, addElementToProductDto);
        return true;
    }

    @DeleteMapping ("/deleteProduct/{id}")
    public boolean deleteProduct(@PathVariable(value="id") Long id) {
        productService.deleteById(id);
        return true;
    }

}
