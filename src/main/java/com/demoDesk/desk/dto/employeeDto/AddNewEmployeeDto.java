package com.demoDesk.desk.dto.employeeDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddNewEmployeeDto {
    private String name;
    private Long departmentId;
}
