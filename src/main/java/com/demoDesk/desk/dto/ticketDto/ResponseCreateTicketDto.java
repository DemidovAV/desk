package com.demoDesk.desk.dto.ticketDto;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import lombok.Data;

import java.util.List;

@Data
public class ResponseCreateTicketDto {
    private List<ProductElementInfo> productElementInfoList;

}
