package com.demoDesk.desk.dto.productDto;


import lombok.Data;

import java.util.List;
@Data
public class ProductTransferEntity {

    private Long id;

    private String art;

    private String title;

    private String description;

    private List<ProductElementInfo> productElementInfoList;

}
