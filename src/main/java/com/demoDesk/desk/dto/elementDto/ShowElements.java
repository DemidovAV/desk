package com.demoDesk.desk.dto.elementDto;

import com.demoDesk.desk.models.nomenclature.Element;
import lombok.Data;

import java.util.List;

@Data
public class ShowElements {
    private String filter;
    private String art;
    private List<Element> elementList;

}
