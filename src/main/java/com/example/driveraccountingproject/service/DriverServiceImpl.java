package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.DriverResponse;
import com.example.driveraccountingproject.mapper.DriverMapper;
import com.example.driveraccountingproject.model.Driver;
import com.example.driveraccountingproject.repository.DriverRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService{
    private final DriverRepository driverRepository;

    private final DriverMapper mapper = Mappers.getMapper(DriverMapper.class);

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public DriverDTO createDriver(DriverDTO driverDTO) {
        Driver newDriver = driverRepository.save(mapper.toEntity(driverDTO));
        return mapper.toDTO(newDriver);
    }

    @Override
    public DriverDTO getDriverById(Long id) {
        Driver driver = driverRepository.findById(id).get();
        return mapper.toDTO(driver);
    }

    @Override
    public void congratulateDriverOnBirthday(Long driverId) {

    }

    @Override
    public void generateInvoice(Long driverId, double amount) {

    }

    @Override
    public DriverDTO updateDriver(DriverDTO driverDTO) {
        Driver newDriver = driverRepository.findById(driverDTO.getId()).get();
        newDriver.setFullName(driverDTO.getFullName());
        newDriver.setPassport(driverDTO.getPassport());
        newDriver.setDriverLicenseCategory(driverDTO.getDriverLicenseCategory());
        newDriver.setDateOfBirth(driverDTO.getDateOfBirth());
        newDriver.setExperience(driverDTO.getExperience());
        newDriver.setFunds(driverDTO.getFunds());
        newDriver.setUpdated(LocalDateTime.now());
        Driver updatedDriver = driverRepository.save(newDriver);
        return mapper.toDTO(updatedDriver);
    }

    @Override
    public DriverResponse getAllDrivers(int pageNo, int pageSize, String sortBy) {

        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Driver> drivers = driverRepository.findAll(pageable);
        List<Driver> driverList = drivers.getContent();

        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setContent(driverList);
        driverResponse.setPageNo(drivers.getNumber());
        driverResponse.setPageSize(drivers.getSize());
        driverResponse.setTotalPages(drivers.getTotalPages());
        driverResponse.setTotalNumberOfElements(drivers.getNumberOfElements());
        driverResponse.setLast(drivers.isLast());

        return driverResponse;
    }

    @Override
    public void deleteDriver(Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        driverRepository.delete(driver);
    }

}
