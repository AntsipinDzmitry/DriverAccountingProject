package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.exception.DriverNotFoundException;
import com.example.driveraccountingproject.exception.InsufficientAccountFundsException;
import com.example.driveraccountingproject.model.Driver;
import com.example.driveraccountingproject.model.DriverAccount;
import com.example.driveraccountingproject.model.fieldenum.Currency;
import com.example.driveraccountingproject.repository.DriverAccountRepository;
import com.example.driveraccountingproject.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTests {
    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverAccountRepository driverAccountRepository;

    private DriverServiceImpl driverService;

    @BeforeEach
    public void setup() {
        driverService = new DriverServiceImpl(driverRepository, driverAccountRepository);
    }

    @Test
    public void DriverService_CreateDriver_ReturnDriverDTO() {

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(1L);
        driverDTO.setFullName("John Doe");

        Driver driverEntity = new Driver();
        driverEntity.setId(1L);
        driverEntity.setFullName("John Doe");

        DriverAccount driverAccountEntity = new DriverAccount();
        driverAccountEntity.setId(1L);
        driverAccountEntity.setDriver(driverEntity);
        driverAccountEntity.setBalance(BigDecimal.ZERO);
        driverAccountEntity.setCurrency(Currency.RED_DOLLAR);

        Mockito.when(driverRepository.save(any(Driver.class))).thenReturn(driverEntity);
        Mockito.when(driverAccountRepository.save(any(DriverAccount.class))).thenReturn(driverAccountEntity);

        DriverDTO createdDriver = driverService.createDriver(driverDTO);

        assertNotNull(createdDriver);
        assertEquals(driverDTO.getId(), createdDriver.getId());
        assertEquals(driverDTO.getFullName(), createdDriver.getFullName());
    }

    @Test
    public void DriverService_GetDriverById_ReturnDriverDTO() {

        Long driverId = 1L;
        String fullName = "John Doe";

        Driver driverEntity = new Driver();
        driverEntity.setId(driverId);
        driverEntity.setFullName(fullName);

        Mockito.when(driverRepository.findById(driverId)).thenReturn(java.util.Optional.of(driverEntity));

        DriverDTO retrievedDriver = driverService.getDriverById(driverId);

        assertNotNull(retrievedDriver);
        assertEquals(driverId, retrievedDriver.getId());
        assertEquals(fullName, retrievedDriver.getFullName());
    }

    @Test
    public void DriverService_GetDriverById_ThrowsDriverNotFoundException() {

        Long driverId = 1L;

        Mockito.when(driverRepository.findById(driverId)).thenReturn(java.util.Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> driverService.getDriverById(driverId));
    }

    @Test
    public void DriverService_UpdateDriver_ReturnDriverDTO() {

        Long driverId = 1L;
        String fullName = "John Doe";

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(driverId);
        driverDTO.setFullName(fullName);

        Driver existingDriverEntity = new Driver();
        existingDriverEntity.setId(driverId);
        existingDriverEntity.setFullName("Jane Smith");

        Driver updatedDriverEntity = new Driver();
        updatedDriverEntity.setId(driverId);
        updatedDriverEntity.setFullName(fullName);

        Mockito.when(driverRepository.findById(driverDTO.getId())).thenReturn(java.util.Optional.of(existingDriverEntity));
        Mockito.when(driverRepository.save(any(Driver.class))).thenReturn(updatedDriverEntity);

        DriverDTO updatedDriver = driverService.updateDriver(driverDTO);

        assertNotNull(updatedDriver);
        assertEquals(driverDTO.getId(), updatedDriver.getId());
        assertEquals(fullName, updatedDriver.getFullName());
    }

    @Test
    public void DriverService_UpdateDriver_ThrowsDriverNotFoundException() {

        Long driverId = 1L;
        String fullName = "John Doe";

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(driverId);
        driverDTO.setFullName(fullName);

        Mockito.when(driverRepository.findById(driverDTO.getId())).thenReturn(java.util.Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> driverService.updateDriver(driverDTO));
    }

    @Test
    public void DriverService_GetAllDrivers_ReturnPageableResponse() {

        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "fullName";

        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setFullName("John Doe");

        Driver driver2 = new Driver();
        driver2.setId(2L);
        driver2.setFullName("Jane Smith");

        List<Driver> driverList = List.of(driver1, driver2);
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        Page<Driver> driversPage = new PageImpl<>(driverList, pageable, driverList.size());

        Mockito.when(driverRepository.findAll(pageable)).thenReturn(driversPage);

        PageableResponse pageableResponse = driverService.getAllDrivers(pageNo, pageSize, sortBy);

        assertNotNull(pageableResponse);
        assertEquals(driverList.size(), pageableResponse.getContent().size());
        assertEquals(pageNo, pageableResponse.getPageNo());
        assertEquals(pageSize, pageableResponse.getPageSize());
        assertEquals(driverList.size(), pageableResponse.getTotalNumberOfElements());
        assertEquals(driverList.size() / pageSize + 1, pageableResponse.getTotalPages());
        assertTrue(pageableResponse.isLast());
    }

    @Test
    public void DriverService_DeleteDriver_DeletionChecking() {

        Long driverId = 1L;

        Driver driverEntity = new Driver();
        driverEntity.setId(driverId);
        driverEntity.setFullName("John Doe");

        Mockito.when(driverRepository.findById(driverId)).thenReturn(java.util.Optional.of(driverEntity));

        assertDoesNotThrow(() -> driverService.deleteDriver(driverId));

        Mockito.verify(driverRepository).delete(driverEntity);
    }

    @Test
    public void DriverService_AddFunds_ReturnResponseDTO() {

        Long driverId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.RED_DOLLAR;

        Driver driverEntity = new Driver();
        driverEntity.setId(driverId);
        driverEntity.setFullName("John Doe");

        DriverAccount driverAccountEntity = new DriverAccount();
        driverAccountEntity.setId(1L);
        driverAccountEntity.setDriver(driverEntity);
        driverAccountEntity.setBalance(BigDecimal.ZERO);
        driverAccountEntity.setCurrency(Currency.GREEN_DOLLAR);
        driverEntity.setAccount(driverAccountEntity);

        Mockito.when(driverRepository.findById(driverId)).thenReturn(java.util.Optional.of(driverEntity));
        Mockito.when(driverRepository.save(any(Driver.class))).thenReturn(driverEntity);

        DriverAccountDTO updatedAccount = driverService.addFunds(driverId, amount, currency);

        assertNotNull(updatedAccount);
        assertEquals(currency, updatedAccount.getCurrency());
        assertTrue(updatedAccount.getBalance().compareTo(amount) == 0);
    }

    @Test
    public void DriverService_AddFunds_ThrowsDriverNotFoundException() {

        Long driverId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.GREEN_DOLLAR;

        Mockito.when(driverRepository.findById(driverId)).thenReturn(java.util.Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> driverService.addFunds(driverId, amount, currency));
    }

    @Test
    public void DriverService_WithdrawFunds_ReturnResponseDTO() {

        Long driverId = 1L;
        BigDecimal amount = BigDecimal.valueOf(50);
        Currency currency = Currency.RED_DOLLAR;

        Driver driverEntity = new Driver();
        driverEntity.setId(driverId);
        driverEntity.setFullName("John Doe");

        DriverAccount driverAccountEntity = new DriverAccount();
        driverAccountEntity.setId(1L);
        driverAccountEntity.setDriver(driverEntity);
        driverAccountEntity.setBalance(amount);
        driverAccountEntity.setCurrency(currency);
        driverEntity.setAccount(driverAccountEntity);

        Mockito.when(driverRepository.findById(driverId)).thenReturn(java.util.Optional.of(driverEntity));
        Mockito.when(driverRepository.save(any(Driver.class))).thenReturn(driverEntity);

        DriverAccountDTO updatedAccount = driverService.withdrawFunds(driverId, amount, currency);

        assertNotNull(updatedAccount);
        assertEquals(currency, updatedAccount.getCurrency());
        assertEquals(BigDecimal.ZERO, updatedAccount.getBalance());
    }

    @Test
    public void DriverService_WithdrawFunds_ThrowsInsufficientAccountFundsException() {

        Long driverId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency currency = Currency.RED_DOLLAR;

        Driver driverEntity = new Driver();
        driverEntity.setId(driverId);
        driverEntity.setFullName("John Doe");

        DriverAccount driverAccountEntity = new DriverAccount();
        driverAccountEntity.setId(1L);
        driverAccountEntity.setDriver(driverEntity);
        driverAccountEntity.setBalance(BigDecimal.ZERO);
        driverAccountEntity.setCurrency(currency);
        driverEntity.setAccount(driverAccountEntity);

        Mockito.when(driverRepository.findById(driverId)).thenReturn(java.util.Optional.of(driverEntity));

        assertThrows(InsufficientAccountFundsException.class, () -> driverService.withdrawFunds(driverId, amount, currency));
    }

}
