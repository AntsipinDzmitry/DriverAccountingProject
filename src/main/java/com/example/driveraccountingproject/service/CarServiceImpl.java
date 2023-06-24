package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.dto.PartDTO;
import com.example.driveraccountingproject.exception.CarNotFoundException;
import com.example.driveraccountingproject.exception.DriverNotFoundException;
import com.example.driveraccountingproject.exception.PartNotFoundException;
import com.example.driveraccountingproject.mapper.CarMapper;
import com.example.driveraccountingproject.mapper.PartMapper;
import com.example.driveraccountingproject.model.Car;
import com.example.driveraccountingproject.model.Driver;
import com.example.driveraccountingproject.model.Part;
import com.example.driveraccountingproject.repository.CarRepository;
import com.example.driveraccountingproject.repository.DriverRepository;
import com.example.driveraccountingproject.repository.PartRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final PartRepository partRepository;
    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);
    private final PartMapper partMapper = Mappers.getMapper(PartMapper.class);

    @Autowired
    public CarServiceImpl(CarRepository carRepository, DriverRepository driverRepository,
                          PartRepository partRepository) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
        this.partRepository = partRepository;
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Driver driver = driverRepository.findById(carDTO.getDriverId()).orElseThrow(() ->
                new DriverNotFoundException("Driver with associated car not found"));
        Car car = carMapper.toEntity(carDTO);
        car.setDriver(driver);
        Car createdCar = carRepository.save(car);
        return carMapper.toDTO(createdCar);
    }

    @Override
    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new CarNotFoundException("Car not found"));
        return carMapper.toDTO(car);
    }

    @Override
    public CarDTO updateCar(CarDTO carDTO) {
        Driver driver = driverRepository.findById(carDTO.getDriverId()).orElseThrow(() ->
                new DriverNotFoundException("Driver with associated car not found"));
        Car car = carRepository.findById(carDTO.getId()).orElseThrow(() ->
                new CarNotFoundException("Car with associated driver not found"));
        if (car.getDriver().getId() != driver.getId()) {
            throw new DriverNotFoundException("This car doesn't belong to this driver");
        }

        car.setBrand(carDTO.getBrand());
        car.setVin(carDTO.getVin());
        car.setStateNumber(carDTO.getStateNumber());
        car.setManufacturerName(carDTO.getManufacturerName());
        car.setYearOfManufacture(carDTO.getYearOfManufacture());
        car.setUpdated(LocalDateTime.now());
        Car updatedCar = carRepository.save(car);
        return carMapper.toDTO(updatedCar);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new CarNotFoundException("Car not found"));
        carRepository.delete(car);
    }

    @Override
    public PageableResponse getAllCars(int pageNo, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Car> cars = carRepository.findAll(pageable);
        List<Car> carList = cars.getContent();
        List<CarDTO> content = carList.stream().map(carMapper::toDTO).collect(Collectors.toList());
        return PageableResponse.builder()
                .content(content)
                .pageNo(cars.getNumber())
                .pageSize(cars.getSize())
                .totalPages(cars.getTotalPages())
                .totalNumberOfElements(cars.getNumberOfElements())
                .isLast(cars.isLast()).build();
    }

    @Override
    public PageableResponse getAllCarsByDriverId(Long driverId, int pageNo, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Car> cars = carRepository.findByDriverId(driverId, pageable);
        List<Car> carList = cars.getContent();
        List<CarDTO> content = carList.stream().map(carMapper::toDTO).collect(Collectors.toList());
        return PageableResponse.builder()
                .content(content)
                .pageNo(cars.getNumber())
                .pageSize(cars.getSize())
                .totalPages(cars.getTotalPages())
                .totalNumberOfElements(cars.getNumberOfElements())
                .isLast(cars.isLast()).build();
    }

    @Override
    public PartDTO installPart(PartDTO partDTO) {
        Car car = carRepository.findById(partDTO.getCarId()).orElseThrow(() ->
                new CarNotFoundException("Car with associated part not found"));
        Part part = partMapper.toEntity(partDTO);
        part.setCar(car);
        Part installedPart = partRepository.save(part);
        return partMapper.toDTO(installedPart);
    }

    @Override
    public PartDTO replacePart(PartDTO partDTO) {
        Car car = carRepository.findById(partDTO.getCarId()).orElseThrow(() ->
                new CarNotFoundException("Car with associated driver not found"));
        Part part = partRepository.findById(partDTO.getId()).orElseThrow(() ->
                new PartNotFoundException("Part with associated car not found"));
        if (part.getCar().getId() != car.getId()) {
            throw new DriverNotFoundException("This part doesn't belong to this car");
        }
        part.setSerialNumber(partDTO.getSerialNumber());
        part.setUpdated(LocalDateTime.now());
        Part replacedPart = partRepository.save(part);
        return partMapper.toDTO(replacedPart);
    }

    @Override
    public PageableResponse getAllPartsByCarId(Long carId, int pageNo, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Part> parts = partRepository.findByCarId(carId, pageable);
        List<Part> partList = parts.getContent();
        List<PartDTO> content = partList.stream().map(partMapper::toDTO).collect(Collectors.toList());
        return PageableResponse.builder()
                .content(content)
                .pageNo(parts.getNumber())
                .pageSize(parts.getSize())
                .totalPages(parts.getTotalPages())
                .totalNumberOfElements(parts.getNumberOfElements())
                .isLast(parts.isLast()).build();
    }
}
