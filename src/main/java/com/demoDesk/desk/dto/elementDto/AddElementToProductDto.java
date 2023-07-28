package com.demoDesk.desk.dto.elementDto;

import com.demoDesk.desk.models.nomenclature.Element;
import lombok.Data;

@Data
public class AddElementToProductDto {
    private Element element;
    private Integer quantity;
    private Long departmentId;
}
