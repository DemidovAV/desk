package com.demoDesk.desk.dto.elementDto;

import com.demoDesk.desk.models.nomenclature.Product;
import lombok.Data;

@Data
/**
 * не используешь
 */
public class ElementProductInfo {
    private Product product;
    private Integer count;
}
