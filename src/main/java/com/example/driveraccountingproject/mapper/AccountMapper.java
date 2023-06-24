package com.example.driveraccountingproject.mapper;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.model.DriverAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    DriverAccountDTO toDTO(DriverAccount driverAccount);

    DriverAccount toEntity(DriverAccountDTO driverAccountDTO);
}
