package com.example.driveraccountingproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    @Min(0)
    private Long id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String passport;
    @NotBlank
    private String driverLicenseCategory;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent
    @NotNull
    private LocalDate dateOfBirth;
    @PositiveOrZero
    private int experience;
    @Valid
    private DriverAccountDTO account;
    @PastOrPresent
    private LocalDateTime created;
    @PastOrPresent
    private LocalDateTime updated;
}
