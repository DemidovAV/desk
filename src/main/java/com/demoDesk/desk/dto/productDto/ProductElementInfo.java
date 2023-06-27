package com.demoDesk.desk.dto.productDto;

import com.demoDesk.desk.models.nomenclature.Element;
import lombok.Data;

@Data
public class ProductElementInfo {
    private Element element;
    private Integer count;
}
