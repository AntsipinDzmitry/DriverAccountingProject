package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.dto.PartDTO;

public interface CarService {
    CarDTO createCar(CarDTO carDTO);
    CarDTO getCarById(Long id);
    CarDTO updateCar(CarDTO carDTO);
    void deleteCar (Long id);
    PageableResponse getAllCars(int pageNo, int pageSize, String sortBy);
    PageableResponse getAllCarsByDriverId(Long driverId, int pageNo, int pageSize, String sortBy);
    PartDTO installPart(PartDTO partDTO);
    PartDTO replacePart(PartDTO partDTO);
    PageableResponse getAllPartsByCarId(Long carId, int pageNo, int pageSize, String sortBy);
}
