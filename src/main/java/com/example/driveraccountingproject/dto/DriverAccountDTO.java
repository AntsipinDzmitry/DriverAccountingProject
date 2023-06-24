package com.example.driveraccountingproject.dto;

import com.example.driveraccountingproject.model.fieldenum.Currency;
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
    private Currency currency;
    private BigDecimal balance;
}
