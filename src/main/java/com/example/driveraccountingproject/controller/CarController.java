package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CarController {
    private final CarService carService;
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("cars")
    public ResponseEntity<PageableResponse> getAllCars(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "brand", required = false) String sortBy
    ) {
        return new ResponseEntity<>(carService.getAllCars(pageNo, pageSize, sortBy), HttpStatus.OK);
    }
    @GetMapping("drivers/{driverId}/cars")
    public ResponseEntity<PageableResponse> getAllCarsByDriverId(
            @PathVariable Long driverId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        return new ResponseEntity<>(carService.getAllCarsByDriverId(driverId, pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("cars/{carId}")
    public ResponseEntity<CarDTO> carDetail(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }
    @PostMapping("cars")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        return new ResponseEntity<>(carService.createCar(carDTO), HttpStatus.CREATED);
    }
    @PutMapping("cars")
    public ResponseEntity<CarDTO> updateCar(@RequestBody CarDTO carDTO) {
        return new ResponseEntity<>(carService.updateCar(carDTO), HttpStatus.OK);
    }
    @DeleteMapping("cars/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>("Driver was deleted successfully", HttpStatus.OK);
    }
}
