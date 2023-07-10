package com.demoDesk.desk.dto.productDto;

import com.demoDesk.desk.models.nomenclature.Product;
import lombok.Data;

import java.util.List;

@Data
public class ShowProductsDto {
    private String filter;
    private String art;
    private List<Product> products;

}
