package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.productDto.ShowProducts;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import com.demoDesk.desk.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductsController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

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
    public ShowProducts showProducts(@RequestParam(value = "filter", required = false) String filter,
                                     @RequestParam(value = "art", required = false) String art) {
        ShowProducts showProducts = new ShowProducts();
        showProducts.setProducts(productService.getProductsWithFiltering(filtration(filter, art)));
        showProducts.setFilter(filter);
        showProducts.setArt(art);

        return showProducts;
    }

    @PostMapping("/reset")
    public ShowProducts showProductListReset() {
        ShowProducts showProducts = new ShowProducts();
        showProducts.setProducts(productService.getAllProducts());
        showProducts.setFilter(null);
        showProducts.setFilter(null);
        return showProducts;
    }
    @GetMapping("/editProduct/{id}")
    public Product showEditProduct(@PathVariable(value="id") Long id){
        return productService.getProductById(id);
    }

    @PostMapping("/editProduct/confirm")
    public String editConfirm(@RequestBody Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }


    @GetMapping("/addProduct")
    public List<Element> addProduct() {
        return productService.getAllElements();
    }

    @PostMapping("/addProduct/confirm")
    public boolean addConfirm(@RequestBody Product product) {
        productService.saveProduct(product);
        return true;
    }

    @GetMapping("/showProduct/{id}")
    public Product showOneProduct(@PathVariable(value="id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/deleteProduct/{id}")
    public boolean deleteProduct(@PathVariable(value="id") Long id) {
        productService.deleteById(id);
        return true;
    }

}
