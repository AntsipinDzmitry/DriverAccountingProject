package com.example.driveraccountingproject.controller;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.FundsDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.model.fieldenum.Currency;
import com.example.driveraccountingproject.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DriverControllerTests {
    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void DriverController_GetAllDrivers_ReturnResponseBodyAndStatus() {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "fullName";
        PageableResponse pageableResponse = new PageableResponse();
        when(driverService.getAllDrivers(pageNo, pageSize, sortBy)).thenReturn(pageableResponse);

        ResponseEntity<PageableResponse> response = driverController.getAllDrivers(pageNo, pageSize, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pageableResponse, response.getBody());
        verify(driverService, times(1)).getAllDrivers(pageNo, pageSize, sortBy);
    }

    @Test
    void DriverController_GetDriverById_ReturnResponseBodyAndStatus() {
        Long driverId = 1L;
        DriverDTO driverDTO = new DriverDTO();
        when(driverService.getDriverById(driverId)).thenReturn(driverDTO);

        ResponseEntity<DriverDTO> response = driverController.driverDetail(driverId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverDTO, response.getBody());
        verify(driverService, times(1)).getDriverById(driverId);
    }

    @Test
    void DriverController_CreateDriver_ReturnResponseBodyAndStatus() {
        DriverDTO driverDTO = new DriverDTO();
        DriverDTO createdDriverDTO = new DriverDTO();
        when(driverService.createDriver(driverDTO)).thenReturn(createdDriverDTO);

        ResponseEntity<DriverDTO> response = driverController.createDriver(driverDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDriverDTO, response.getBody());
        verify(driverService, times(1)).createDriver(driverDTO);
    }

    @Test
    void DriverController_UpdateDriver_ReturnResponseBodyAndStatus() {
        DriverDTO driverDTO = new DriverDTO();
        DriverDTO updatedDriverDTO = new DriverDTO();
        when(driverService.updateDriver(driverDTO)).thenReturn(updatedDriverDTO);

        ResponseEntity<DriverDTO> response = driverController.updateDriver(driverDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDriverDTO, response.getBody());
        verify(driverService, times(1)).updateDriver(driverDTO);
    }

    @Test
    void DriverController_testDeleteDriver_ReturnStringAndStatus() {
        Long driverId = 1L;

        ResponseEntity<String> response = driverController.deleteDriver(driverId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver was deleted successfully", response.getBody());
        verify(driverService, times(1)).deleteDriver(driverId);
    }

    @Test
    void DriverController_AddFunds_ReturnResponseBodyAndStatus() {
        Long driverId = 1L;
        FundsDTO fundsDTO = new FundsDTO();
        DriverAccountDTO driverAccountDTO = new DriverAccountDTO();
        when(driverService.addFunds(driverId, fundsDTO.getAmount(), fundsDTO.getCurrency())).thenReturn(driverAccountDTO);

        ResponseEntity<DriverAccountDTO> response = driverController.addFunds(driverId, fundsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverAccountDTO, response.getBody());
        verify(driverService, times(1)).addFunds(driverId, fundsDTO.getAmount(), fundsDTO.getCurrency());
    }

    @Test
    void DriverController_WithdrawFunds_ReturnResponseBodyAndStatus() {
        Long driverId = 1L;
        FundsDTO fundsDTO = new FundsDTO();
        DriverAccountDTO driverAccountDTO = new DriverAccountDTO();
        when(driverService.withdrawFunds(driverId, fundsDTO.getAmount(), fundsDTO.getCurrency())).thenReturn(driverAccountDTO);

        ResponseEntity<DriverAccountDTO> response = driverController.withdrawFunds(driverId, fundsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverAccountDTO, response.getBody());
        verify(driverService, times(1)).withdrawFunds(driverId, fundsDTO.getAmount(), fundsDTO.getCurrency());
    }

    @Test
    void DriverController_GetAccountStatus_ReturnResponseBodyAndStatus() {
        Long driverId = 1L;
        Currency currency = Currency.GREEN_DOLLAR;
        DriverAccountDTO driverAccountDTO = new DriverAccountDTO();
        when(driverService.getAccountStatus(driverId, currency)).thenReturn(driverAccountDTO);

        ResponseEntity<DriverAccountDTO> response = driverController.getAccountStatus(driverId, currency);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverAccountDTO, response.getBody());
        verify(driverService, times(1)).getAccountStatus(driverId, currency);
    }
}
