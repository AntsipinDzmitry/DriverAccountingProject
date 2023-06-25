package com.example.driveraccountingproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartDTO {
    @Min(0)
    private Long id;
    @NotNull
    private Long carId;
    @NotBlank
    private String serialNumber;

    @PastOrPresent
    private LocalDateTime created;
    @PastOrPresent
    private LocalDateTime updated;
}
