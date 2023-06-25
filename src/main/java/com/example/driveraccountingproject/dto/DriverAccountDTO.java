package com.example.driveraccountingproject.dto;

import com.example.driveraccountingproject.model.fieldenum.Currency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverAccountDTO {
    @NotNull
    private Currency currency;
    @DecimalMin(value = "0.0")
    private BigDecimal balance;
}
