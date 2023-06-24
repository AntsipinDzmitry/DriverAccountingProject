package com.example.driveraccountingproject.mapper;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(target = "driverId",
            expression = "java(car.getDriver().getId())")
    CarDTO toDTO(Car car);

    Car toEntity(CarDTO carDTO);
}
