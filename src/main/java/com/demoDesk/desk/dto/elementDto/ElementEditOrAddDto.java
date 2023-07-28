package com.demoDesk.desk.dto.elementDto;

import com.demoDesk.desk.models.nomenclature.Element;
import lombok.Data;

@Data
public class ElementEditOrAddDto {
    private Element element;
    private Long departmentId;
}
