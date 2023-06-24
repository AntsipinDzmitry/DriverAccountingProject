package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.PartDTO;

public interface PartService {
    PartDTO createPart(PartDTO partDTO);
    PartDTO getPartById(Long id);
    PartDTO updatePart(Long id);
    void deletePart(Long partId);
}
