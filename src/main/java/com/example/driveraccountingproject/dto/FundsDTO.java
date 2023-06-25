package com.example.driveraccountingproject.dto;

import com.example.driveraccountingproject.model.fieldenum.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundsDTO {
    @NotNull
    private Currency currency;
    @PositiveOrZero
    private BigDecimal amount;
}
