package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.DriverResponse;
import com.example.driveraccountingproject.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {
    DriverDTO createDriver(DriverDTO driverDTO);
    DriverDTO getDriverById(Long id);
    void congratulateDriverOnBirthday(Long driverId);
    void generateInvoice(Long driverId, double amount);
    DriverDTO updateDriver(DriverDTO driverDTO);
    DriverResponse getAllDrivers(int pageNo, int pageSize, String sortBy);
    void deleteDriver(Long driverId);
}
