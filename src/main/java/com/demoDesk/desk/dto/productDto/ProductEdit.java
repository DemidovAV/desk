package com.demoDesk.desk.dto.productDto;

import com.demoDesk.desk.models.nomenclature.Element;
import lombok.Data;

import java.util.List;
@Data
public class ProductEdit {
    private ProductTransferEntity productTransfer;
    private List<Element> elements;
}
