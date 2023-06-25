package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.FundsDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.model.fieldenum.Currency;
import com.example.driveraccountingproject.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
@Tag(name = "Driver controller", description = "Maintain information about our customers")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping()
    @Operation(
            summary = "get all drivers in system",
            description = "All our drivers"
    )
    public ResponseEntity<PageableResponse> getAllDrivers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "fullName", required = false) String sortBy
    ) {
        return new ResponseEntity<>(driverService.getAllDrivers(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "get particular driver by his id",
            description = "Certain driver"
    )
    public ResponseEntity<DriverDTO> driverDetail(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PostMapping()
    @Operation(
            summary = "Adding new customer to our system",
            description = "New member of our team"
    )
    public ResponseEntity<DriverDTO> createDriver(@RequestBody DriverDTO driverDTO) {
        return new ResponseEntity<>(driverService.createDriver(driverDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    @Operation(
            summary = "Update certain driver",
            description = "Update certain driver"
    )
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody DriverDTO driverDTO) {
        return new ResponseEntity<>(driverService.updateDriver(driverDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete certain driver",
            description = "Delete certain driver"
    )
    public ResponseEntity<String> deleteDriver(@PathVariable("id") Long driverId) {
        driverService.deleteDriver(driverId);
        return new ResponseEntity<>("Driver was deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{driverId}/add")
    @Operation(
            summary = "Functionality for possibility to add money to customer's account",
            description = "Add money"
    )
    public ResponseEntity<DriverAccountDTO> addFunds(@PathVariable Long driverId, @RequestBody FundsDTO fundsDTO) {
        return new ResponseEntity<>(driverService.addFunds(driverId, fundsDTO.getAmount(),
                fundsDTO.getCurrency()), HttpStatus.OK);
    }

    @PutMapping("/{driverId}/withdraw")
    @Operation(
            summary = "Functionality for possibility to withdraw money to customer's account",
            description = "Withdraw money"
    )
    public ResponseEntity<DriverAccountDTO> withdrawFunds(@PathVariable Long driverId, @RequestBody FundsDTO fundsDTO) {
        return new ResponseEntity<>(driverService.withdrawFunds(driverId, fundsDTO.getAmount(),
                fundsDTO.getCurrency()), HttpStatus.OK);
    }

    @GetMapping("/{driverId}/account")
    @Operation(
            summary = "Check account of particular customer with checking preferable currency",
            description = "Get balance"
    )
    public ResponseEntity<DriverAccountDTO> getAccountStatus(
            @PathVariable Long driverId, @RequestParam(defaultValue = "GREEN_DOLLAR", required = false) Currency currency) {
        DriverAccountDTO accountDTO = driverService.getAccountStatus(driverId, currency);
        return ResponseEntity.ok(accountDTO);
    }
}
