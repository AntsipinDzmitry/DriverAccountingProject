package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.model.Part;

public interface PartService {
    Part createPart(Part part);
    Part getPartById(Long id);
}
