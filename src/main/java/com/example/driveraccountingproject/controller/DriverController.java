package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.DriverResponse;
import com.example.driveraccountingproject.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    @GetMapping("drivers")
    public ResponseEntity<DriverResponse> getAllDrivers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "fullName", required = false) String sortBy
    ) {
        return new ResponseEntity<>(driverService.getAllDrivers(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("drivers/{id}")
    public ResponseEntity<DriverDTO> driverDetail(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PostMapping("drivers")
    public ResponseEntity<DriverDTO> createDriver(@RequestBody DriverDTO driverDTO) {
        return new ResponseEntity<>(driverService.createDriver(driverDTO), HttpStatus.CREATED);
    }
    @PutMapping("drivers")
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO driverDTO) {
        return new ResponseEntity<>(driverService.updateDriver(driverDTO), HttpStatus.OK);
    }
    @DeleteMapping("drivers/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable("id") Long driverId) {
        driverService.deleteDriver(driverId);
        return new ResponseEntity<>("Driver was deleted successfully", HttpStatus.OK);
    }
}
