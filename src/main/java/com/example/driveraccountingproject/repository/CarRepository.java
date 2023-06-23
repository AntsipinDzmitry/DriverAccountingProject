package com.example.driveraccountingproject.repository;

import com.example.driveraccountingproject.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
