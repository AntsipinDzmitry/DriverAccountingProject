package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Tag(name = "Car controller", description = "Maintain information about driver's cars")
public class CarController {
    private final CarService carService;
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("cars")
    @Operation(
            summary = "get all cars in system",
            description = "All our cars"
    )
    public ResponseEntity<PageableResponse> getAllCars(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "brand", required = false) String sortBy
    ) {
        return new ResponseEntity<>(carService.getAllCars(pageNo, pageSize, sortBy), HttpStatus.OK);
    }
    @GetMapping("drivers/{driverId}/cars")
    @Operation(
            summary = "get all cars by DriverId",
            description = "All driver-related courses"
    )
    public ResponseEntity<PageableResponse> getAllCarsByDriverId(
            @PathVariable Long driverId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        return new ResponseEntity<>(carService.getAllCarsByDriverId(driverId, pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("cars/{carId}")
    @Operation(
            summary = "get particular car by its id",
            description = "Certain car"
    )
    public ResponseEntity<CarDTO> carDetail(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }
    @PostMapping("cars")
    @Operation(
            summary = "New car creation",
            description = "great chance to create new car"
    )
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        return new ResponseEntity<>(carService.createCar(carDTO), HttpStatus.CREATED);
    }
    @PutMapping("cars")
    @Operation(
            summary = "Update certain car",
            description = "Update certain car"
    )
    public ResponseEntity<CarDTO> updateCar(@RequestBody CarDTO carDTO) {
        return new ResponseEntity<>(carService.updateCar(carDTO), HttpStatus.OK);
    }
    @DeleteMapping("cars/{carId}")
    @Operation(
            summary = "Delete certain car",
            description = "Delete certain car"
    )
    public ResponseEntity<String> deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>("Driver was deleted successfully", HttpStatus.OK);
    }
}
