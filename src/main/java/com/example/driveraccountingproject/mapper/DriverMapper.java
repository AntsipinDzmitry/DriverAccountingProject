package com.example.driveraccountingproject.mapper;

import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.model.Driver;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DriverMapper {

    DriverDTO toDTO(Driver driver);

    Driver toEntity(DriverDTO driverDTO);
}
