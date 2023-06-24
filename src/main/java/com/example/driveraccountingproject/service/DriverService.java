package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.PageableResponse;

public interface DriverService {
    DriverDTO createDriver(DriverDTO driverDTO);
    DriverDTO getDriverById(Long id);
    void congratulateDriverOnBirthday(Long driverId);
    void generateInvoice(Long driverId, double amount);
    DriverDTO updateDriver(DriverDTO driverDTO);
    PageableResponse getAllDrivers(int pageNo, int pageSize, String sortBy);
    void deleteDriver(Long driverId);
}
