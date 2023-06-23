package com.example.driveraccountingproject.repository;

import com.example.driveraccountingproject.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
}
