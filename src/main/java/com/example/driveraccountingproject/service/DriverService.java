package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.model.fieldenum.Currency;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
@Validated
public interface DriverService {
    DriverDTO createDriver(@Valid DriverDTO driverDTO);
    DriverDTO getDriverById(@Valid Long id);
    void congratulateDriverOnBirthday(@Valid Long driverId);
    DriverDTO updateDriver(@Valid DriverDTO driverDTO);
    PageableResponse getAllDrivers(@Valid int pageNo, @Valid int pageSize, @Valid String sortBy);
    void deleteDriver(@Valid Long driverId);
    DriverAccountDTO addFunds(@Valid Long driverId, @Valid BigDecimal amount, @Valid Currency currency);
    DriverAccountDTO withdrawFunds(@Valid Long driverId, @Valid BigDecimal amount, @Valid Currency currency);
    DriverAccountDTO getAccountStatus(@Valid Long driverId, @Valid Currency currency);
    BigDecimal convertCurrency(@Valid BigDecimal amount, @Valid Currency fromCurrency, @Valid Currency toCurrency);
    BigDecimal getConversionRate(@Valid Currency fromCurrency, @Valid Currency toCurrency);


}
