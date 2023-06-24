package com.example.driveraccountingproject.repository;

import com.example.driveraccountingproject.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByDriverId(Long driverId, Pageable pageable);
}
