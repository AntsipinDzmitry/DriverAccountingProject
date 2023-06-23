package com.example.driveraccountingproject.dto;

import com.example.driveraccountingproject.model.Driver;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponse {
    private List<Driver> content;
    @Min(0)
    private int pageNo;
    @Min(0)
    private int pageSize;
    private long totalNumberOfElements;
    private int totalPages;
    private boolean isLast;
}
