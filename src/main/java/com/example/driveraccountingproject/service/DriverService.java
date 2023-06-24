package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.model.fieldenum.Currency;

import java.math.BigDecimal;

public interface DriverService {
    DriverDTO createDriver(DriverDTO driverDTO);
    DriverDTO getDriverById(Long id);
    void congratulateDriverOnBirthday(Long driverId);
    DriverDTO updateDriver(DriverDTO driverDTO);
    PageableResponse getAllDrivers(int pageNo, int pageSize, String sortBy);
    void deleteDriver(Long driverId);
    DriverAccountDTO addFunds(Long driverId, BigDecimal amount, Currency currency);
    DriverAccountDTO withdrawFunds(Long driverId, BigDecimal amount, Currency currency);
    DriverAccountDTO getAccountStatus(Long driverId, Currency currency);
    BigDecimal convertCurrency(BigDecimal amount, Currency fromCurrency, Currency toCurrency);
    BigDecimal getConversionRate(Currency fromCurrency, Currency toCurrency);


}
