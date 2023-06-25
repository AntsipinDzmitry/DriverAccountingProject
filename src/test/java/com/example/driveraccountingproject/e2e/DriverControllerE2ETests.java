package com.example.driveraccountingproject.e2e;

import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.profiles.active=test")
public class DriverControllerE2ETests {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/";
    }

    @Test
    public void DriverController_GetAllDrivers_ReturnsListOfDrivers() {

        ResponseEntity<PageableResponse> response = restTemplate.getForEntity(
                getBaseUrl() + "drivers?pageNo=0&pageSize=10&sortBy=fullName", PageableResponse.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PageableResponse pageableResponse = response.getBody();
        assertThat(pageableResponse).isNotNull();
        assertFalse(pageableResponse.getContent().isEmpty());
    }

    @Test
    public void DriverController_CreateDriver_SuccessfullyCreatesNewDriver() {

        DriverDTO newDriver = DriverDTO.builder()
                .fullName("User test")
                .passport("AB7654321")
                .driverLicenseCategory("B")
                .experience(5)
                .dateOfBirth(LocalDate.of(1986, 11, 23))
                .build();

        ResponseEntity<DriverDTO> response = restTemplate.postForEntity(
                getBaseUrl() + "drivers", newDriver, DriverDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        DriverDTO createdDriver = response.getBody();
        assertThat(createdDriver).isNotNull();
        assertThat(createdDriver.getId()).isNotNull();
        assertThat(createdDriver.getFullName()).isEqualTo(newDriver.getFullName());
    }

    @Test
    public void DriverController_UpdateDriver_SuccessfullyUpdatesDriverInformation() {

        DriverDTO existingDriver = new DriverDTO();
        existingDriver.setId(1L);
        existingDriver.setFullName("John Doe");
        restTemplate.put(getBaseUrl() + "drivers", existingDriver);
        ResponseEntity<DriverDTO> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "drivers/{id}", DriverDTO.class, existingDriver.getId());

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DriverDTO updatedDriver = getResponse.getBody();
        assertThat(updatedDriver).isNotNull();
        assertThat(updatedDriver.getId()).isEqualTo(existingDriver.getId());
        assertThat(updatedDriver.getFullName()).isEqualTo(existingDriver.getFullName());
    }

}