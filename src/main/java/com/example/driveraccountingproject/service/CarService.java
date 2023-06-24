package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.dto.PageableResponse;

public interface CarService {
    CarDTO createCar(CarDTO carDTO);
    CarDTO getCarById(Long id);
    CarDTO updateCar(CarDTO carDTO);
    void deleteCar (Long id);
    PageableResponse getAllCars(int pageNo, int pageSize, String sortBy);
    PageableResponse getAllCarsByDriverId(Long driverId, int pageNo, int pageSize, String sortBy);
}
