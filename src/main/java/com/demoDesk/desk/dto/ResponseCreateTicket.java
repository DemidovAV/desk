package com.demoDesk.desk.dto;

import com.demoDesk.desk.dto.productDto.ProductElementInfo;
import lombok.Data;

import java.util.List;

@Data
public class ResponseCreateTicket {
    private List<ProductElementInfo> productElementInfoList;

}
