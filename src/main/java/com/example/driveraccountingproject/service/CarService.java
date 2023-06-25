package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.dto.PartDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CarService {
    CarDTO createCar(@Valid CarDTO carDTO);
    CarDTO getCarById(@Valid Long id);
    CarDTO updateCar(@Valid CarDTO carDTO);
    void deleteCar (@Valid Long id);
    PageableResponse getAllCars(@Valid int pageNo, @Valid int pageSize, @Valid String sortBy);
    PageableResponse getAllCarsByDriverId(@Valid Long driverId, @Valid int pageNo, @Valid int pageSize, @Valid String sortBy);
    PartDTO installPart(@Valid PartDTO partDTO);
    PartDTO replacePart(@Valid PartDTO partDTO);
    PageableResponse getAllPartsByCarId(@Valid Long carId, @Valid int pageNo, @Valid int pageSize, @Valid String sortBy);
}
