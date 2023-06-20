package com.demoDesk.desk.controllers;

import com.demoDesk.desk.models.Product;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import com.demoDesk.desk.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProducts(Model model,
                               @RequestParam(value = "filter", required = false) String filter) {
        Product product = new Product();
        Specification<Product> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(ProductSpec.titleContains(filter));
        }
        List<Product> filteredProducts = productService.getProductsWithFiltering(spec);

        model.addAttribute("product", product);
        model.addAttribute("products", filteredProducts);
        model.addAttribute("filter", filter);
        return "products";
    }

    @PostMapping("/reset")
    public String showProductListReset(Model model) {
        Product product = new Product();
        String filter = null;
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", product);
        model.addAttribute("filter", filter);
        return "redirect:/products";
    }
    @GetMapping("/editProduct/{id}")
    public String showEditProduct(Model model, @PathVariable(value="id") Long id){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/editProduct/confirm")
    public String editConfirm(@ModelAttribute(value="product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }


    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-product";
    }

    @PostMapping("/addProduct/confirm")
    public String addConfirm(@ModelAttribute(value="product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/showProduct/{id}")
    public String showOneProduct(Model model, @PathVariable(value="id") Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "show-product";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value="id") Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

}
