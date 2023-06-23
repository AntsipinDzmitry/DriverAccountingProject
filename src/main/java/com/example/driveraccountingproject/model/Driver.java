package com.example.driveraccountingproject.model;

import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.mapper.DriverMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver implements DriverMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String passport;
    private String driverLicenseCategory;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private int experience;
    private double funds;

    @CreationTimestamp
    private LocalDateTime created;
    @CreationTimestamp
    private LocalDateTime updated;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();


    @Override
    public DriverDTO toDTO(Driver driver) {
        return new DriverDTO();
    }

    @Override
    public Driver toEntity(DriverDTO driverDTO) {
        return null;
    }
}
