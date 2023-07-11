package com.demoDesk.desk.dto.ticketDto;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import com.demoDesk.desk.models.enums.Priority;
import com.demoDesk.desk.models.nomenclature.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TicketCreationDto {

    private String title;
    private Product product;
    private Integer quantity;
    private Priority priority;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp expirationDate;
    private String comment;
    List<ProductElementInfo> productElementInfos;
}
