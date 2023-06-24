package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.dto.PartDTO;
import com.example.driveraccountingproject.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PartController {
    private final CarService carService;

    @Autowired
    public PartController(CarService carService) {
        this.carService = carService;
    }
    @GetMapping("cars/{carId}/parts")
    public ResponseEntity<PageableResponse> getAllPartsByCarId(
            @PathVariable Long carId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        return new ResponseEntity<>(carService.getAllCarsByDriverId(carId, pageNo, pageSize, sortBy), HttpStatus.OK);
    }
    @PostMapping("parts")
    public ResponseEntity<PartDTO> installPart(@RequestBody PartDTO partDTO) {
        return new ResponseEntity<>(carService.installPart(partDTO), HttpStatus.CREATED);
    }
    @PutMapping("parts")
    public ResponseEntity<PartDTO> replacePart(@RequestBody PartDTO partDTO) {
        return new ResponseEntity<>(carService.replacePart(partDTO), HttpStatus.OK);
    }
}
