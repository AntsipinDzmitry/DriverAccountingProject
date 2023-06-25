package com.example.driveraccountingproject.dto;

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
public class CarDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long driverId;
    @NotBlank
    private String vin;
    @NotBlank
    private String stateNumber;
    private String manufacturerName;
    private String brand;
    @PastOrPresent
    private int yearOfManufacture;

    @PastOrPresent
    private LocalDateTime created;
    @PastOrPresent
    private LocalDateTime updated;
}
