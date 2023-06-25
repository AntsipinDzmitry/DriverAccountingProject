package com.example.driveraccountingproject.dto;

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
public class PageableResponse {
    private List<?> content;
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    private int pageNo;
    @Min(value = 0, message = "Page size must be greater than or equal to 0")
    private int pageSize;
    @Min(value = 0, message = "Total number of elements must be greater than or equal to 0")
    private long totalNumberOfElements;
    @Min(value = 0, message = "Total number of pages must be greater than or equal to 0")
    private int totalPages;
    private boolean isLast;
}
