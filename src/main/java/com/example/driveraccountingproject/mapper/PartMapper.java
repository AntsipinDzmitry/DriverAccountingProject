package com.example.driveraccountingproject.mapper;

import com.example.driveraccountingproject.dto.PartDTO;
import com.example.driveraccountingproject.model.Part;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PartMapper {
    PartDTO toDTO(Part part);

    Part toEntity(PartDTO partDTO);
}
