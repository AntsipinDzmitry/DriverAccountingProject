package com.example.driveraccountingproject.mapper;

import com.example.driveraccountingproject.dto.PartDTO;
import com.example.driveraccountingproject.model.Part;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartMapper {
    @Mapping(target = "carId",
            expression = "java(part.getCar().getId())")
    PartDTO toDTO(Part part);

    Part toEntity(PartDTO partDTO);
}
