package com.example.driveraccountingproject.mapper;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.model.Car;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarMapper {
    CarDTO toDTO(Car car);

    Car toEntity(CarDTO carDTO);
}
