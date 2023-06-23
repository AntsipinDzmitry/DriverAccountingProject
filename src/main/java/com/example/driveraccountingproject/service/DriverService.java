package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.model.Driver;

public interface DriverService {
    Driver createDriver(Driver driver);
    Driver getDriverById(Long id);
}
