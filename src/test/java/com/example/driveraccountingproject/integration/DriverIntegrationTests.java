package com.example.driveraccountingproject.integration;

import com.example.driveraccountingproject.dto.PageableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.service.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DriverIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DriverService driverService;

    DriverDTO driverDTO;

    @BeforeEach
    public void init() {
        driverDTO = DriverDTO.builder()
                .fullName("Bari Alibasov")
                .passport("AB1234567" + Math.random())
                .driverLicenseCategory("E")
                .experience(34)
                .dateOfBirth(LocalDate.of(1956, 11, 23))
                .build();
    }

    @Test
    public void CreateDriverTest() throws Exception {

        MvcResult createDriverResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/drivers")
                        .content(objectMapper.writeValueAsString(driverDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseContent = createDriverResult.getResponse().getContentAsString();
        DriverDTO createdDriver = objectMapper.readValue(responseContent, DriverDTO.class);

        assertThat(createdDriver.getId()).isNotNull();
        assertThat(createdDriver.getFullName()).isEqualTo(driverDTO.getFullName());
        assertThat(createdDriver.getPassport()).isEqualTo(driverDTO.getPassport());
        assertThat(createdDriver.getDriverLicenseCategory()).isEqualTo(driverDTO.getDriverLicenseCategory());
    }

    @Test
    public void GetAllDriversTest() throws Exception {

        MvcResult getAllDriversResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/drivers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = getAllDriversResult.getResponse().getContentAsString();
        PageableResponse pageableResponse = objectMapper.readValue(responseContent, PageableResponse.class);

        assertThat(pageableResponse.getContent()).isNotEmpty();
    }
    @Test
    public void GetDriverByIdTest() throws Exception {

        DriverDTO createdDriver = driverService.createDriver(driverDTO);

        MvcResult getDriverByIdResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/drivers/{id}", createdDriver.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = getDriverByIdResult.getResponse().getContentAsString();
        DriverDTO retrievedDriver = objectMapper.readValue(responseContent, DriverDTO.class);

        assertThat(retrievedDriver.getId()).isEqualTo(createdDriver.getId());
        assertThat(retrievedDriver.getFullName()).isEqualTo(createdDriver.getFullName());
        assertThat(retrievedDriver.getPassport()).isEqualTo(createdDriver.getPassport());
        assertThat(retrievedDriver.getDriverLicenseCategory()).isEqualTo(createdDriver.getDriverLicenseCategory());
    }

    @Test
    public void UpdateDriverTest() throws Exception {

        DriverDTO createdDriver = driverService.createDriver(driverDTO);

        createdDriver.setPassport("AS1222222");

        MvcResult updateDriverResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/drivers")
                        .content(objectMapper.writeValueAsString(createdDriver))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = updateDriverResult.getResponse().getContentAsString();
        DriverDTO updatedDriver = objectMapper.readValue(responseContent, DriverDTO.class);

        assertThat(updatedDriver.getId()).isEqualTo(createdDriver.getId());
        assertThat(updatedDriver.getFullName()).isEqualTo(createdDriver.getFullName());
        assertThat(updatedDriver.getPassport()).isEqualTo(createdDriver.getPassport());
        assertThat(updatedDriver.getDriverLicenseCategory()).isEqualTo(createdDriver.getDriverLicenseCategory());
    }


}

