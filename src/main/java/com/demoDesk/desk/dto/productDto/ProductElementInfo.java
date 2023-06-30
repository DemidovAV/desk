package com.demoDesk.desk.dto.productDto;

import com.demoDesk.desk.models.nomenclature.Element;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductElementInfo {
    private Element element;
    private Integer count;

    public ProductElementInfo(Element element, Integer count) {
        this.element = element;
        this.count = count;
    }

}
