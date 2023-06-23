package com.example.driveraccountingproject.repository;

import com.example.driveraccountingproject.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
