package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.model.Car;
import com.example.driveraccountingproject.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    Car createCar(Car car);
    Car getCarById(Long id);
    Car updateCar(Car car);
    void deleteCar (Long id);
    Page<Car> getAllCars(Pageable pageable);
}
