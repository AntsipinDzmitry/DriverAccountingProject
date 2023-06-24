package com.example.driveraccountingproject.repository;

import com.example.driveraccountingproject.model.DriverAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverAccountRepository extends JpaRepository<DriverAccount, Long> {
}
