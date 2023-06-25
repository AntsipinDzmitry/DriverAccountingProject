package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.dto.PartDTO;
import com.example.driveraccountingproject.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Tag(name = "Part controller", description = "Management of car parts")
public class PartController {
    private final CarService carService;

    @Autowired
    public PartController(CarService carService) {
        this.carService = carService;
    }
    @GetMapping("cars/{carId}/parts")
    @Operation(
            summary = "get all parts for certain car",
            description = "All car parts"
    )
    public ResponseEntity<PageableResponse> getAllPartsByCarId(
            @PathVariable Long carId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        return new ResponseEntity<>(carService.getAllCarsByDriverId(carId, pageNo, pageSize, sortBy), HttpStatus.OK);
    }
    @PostMapping("parts")
    @Operation(
            summary = "Installation of new part",
            description = "New part"
    )
    public ResponseEntity<PartDTO> installPart(@RequestBody PartDTO partDTO) {
        return new ResponseEntity<>(carService.installPart(partDTO), HttpStatus.CREATED);
    }
    @PutMapping("parts")
    @Operation(
            summary = "Functionality of changing parts one to another",
            description = "Parts changing"
    )
    public ResponseEntity<PartDTO> replacePart(@RequestBody PartDTO partDTO) {
        return new ResponseEntity<>(carService.replacePart(partDTO), HttpStatus.OK);
    }
}
