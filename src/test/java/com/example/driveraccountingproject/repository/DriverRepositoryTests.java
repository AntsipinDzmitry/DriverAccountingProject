package com.example.driveraccountingproject.repository;

import com.example.driveraccountingproject.model.Driver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DriverRepositoryTests {

    @Autowired
    private DriverRepository driverRepository;

    @Test
    public void DriverRepository_SaveDriver_ReturnSavedDriver() {

        Driver driver = Driver.builder()
                .fullName("John")
                .passport("BA1361359")
                .driverLicenseCategory("B")
                .dateOfBirth(LocalDate.now())
                .experience(10)
                .build();

        Driver savedDriver = driverRepository.save(driver);

        Assertions.assertThat(savedDriver).isNotNull();
        Assertions.assertThat(savedDriver.getId()).isGreaterThan(0);
    }

    @Test
    public void DriverRepository_GetAll_ReturnMoreThenOneDriver() {
        Driver driver1 = Driver.builder()
                .fullName("John")
                .passport("BA1361359")
                .driverLicenseCategory("B")
                .dateOfBirth(LocalDate.now())
                .experience(10)
                .build();
        Driver driver2 = Driver.builder()
                .fullName("Sara")
                .passport("BA1381359")
                .driverLicenseCategory("A")
                .dateOfBirth(LocalDate.now())
                .experience(1)
                .build();

        driverRepository.save(driver1);
        driverRepository.save(driver2);
        List<Driver> drivers = driverRepository.findAll();

        Assertions.assertThat(drivers).isNotNull();
        Assertions.assertThat(drivers.size()).isEqualTo(2);
    }

    @Test
    public void DriverRepository_FindById_ReturnDriver() {
        Driver driver = Driver.builder()
                .fullName("Oleg")
                .passport("BA1361")
                .driverLicenseCategory("E")
                .dateOfBirth(LocalDate.now())
                .experience(34)
                .build();
        driverRepository.save(driver);

        Driver checkedDriver = driverRepository.findById(driver.getId()).get();

        Assertions.assertThat(checkedDriver).isNotNull();
    }

    @Test
    public void DriverRepository_UpdateDriver_ReturnDriverNotNull() {
        Driver driver = Driver.builder()
                .fullName("Oleg")
                .passport("BA1361")
                .driverLicenseCategory("E")
                .dateOfBirth(LocalDate.now())
                .experience(34)
                .build();
        driverRepository.save(driver);

        Driver savedDriver = driverRepository.findById(driver.getId()).get();
        savedDriver.setFullName("Kenny");
        savedDriver.setPassport("HY4934600");
        Driver updatedDriver = driverRepository.save(savedDriver);

        Assertions.assertThat(updatedDriver).isNotNull();
        Assertions.assertThat(updatedDriver.getFullName()).isNotNull();
        Assertions.assertThat(updatedDriver.getPassport()).isNotNull();
    }

    @Test
    public void CitizenRepository_DeleteCitizen_ReturnCitizenIsEmpty() {

        Driver driver = Driver.builder()
                .fullName("Oleg")
                .passport("BA1361")
                .driverLicenseCategory("E")
                .dateOfBirth(LocalDate.now())
                .experience(34)
                .build();
        driverRepository.save(driver);

        driverRepository.deleteById(driver.getId());
        Optional<Driver> deletedDriver = driverRepository.findById(driver.getId());

        Assertions.assertThat(deletedDriver).isEmpty();

    }

}
