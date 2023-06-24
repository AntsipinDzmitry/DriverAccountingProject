package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.FundsDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.model.fieldenum.Currency;
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
    public ResponseEntity<PageableResponse> getAllDrivers(
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

    @PutMapping("drivers/{driverId}/add")
    public ResponseEntity<DriverAccountDTO> addFunds(@PathVariable Long driverId, @RequestBody FundsDTO fundsDTO) {
        return new ResponseEntity<>(driverService.addFunds(driverId, fundsDTO.getAmount(),
                fundsDTO.getCurrency()), HttpStatus.OK);
    }

    @PutMapping("drivers/{driverId}/withdraw")
    public ResponseEntity<DriverAccountDTO> withdrawFunds(@PathVariable Long driverId, @RequestBody FundsDTO fundsDTO) {
        return new ResponseEntity<>(driverService.withdrawFunds(driverId, fundsDTO.getAmount(),
                fundsDTO.getCurrency()), HttpStatus.OK);
    }

    @GetMapping("drivers/{driverId}/account")
    public ResponseEntity<DriverAccountDTO> getAccountStatus(
            @PathVariable Long driverId, @RequestParam(defaultValue = "GREEN_DOLLAR", required = false) Currency currency) {
        DriverAccountDTO accountDTO = driverService.getAccountStatus(driverId, currency);
        return ResponseEntity.ok(accountDTO);
    }
}
