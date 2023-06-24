package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.CarDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.exception.CarNotFoundException;
import com.example.driveraccountingproject.exception.DriverNotFoundException;
import com.example.driveraccountingproject.mapper.CarMapper;
import com.example.driveraccountingproject.model.Car;
import com.example.driveraccountingproject.model.Driver;
import com.example.driveraccountingproject.repository.CarRepository;
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
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final CarMapper mapper = Mappers.getMapper(CarMapper.class);

    @Autowired
    public CarServiceImpl(CarRepository carRepository, DriverRepository driverRepository) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Driver driver = driverRepository.findById(carDTO.getDriverId()).orElseThrow(() ->
                new DriverNotFoundException("Driver with associated car not found"));
        Car car = mapper.toEntity(carDTO);
        car.setDriver(driver);
        Car createdCar = carRepository.save(car);
        return mapper.toDTO(createdCar);
    }

    @Override
    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new CarNotFoundException("Car not found"));
        return mapper.toDTO(car);
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
        return mapper.toDTO(updatedCar);
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
        List<CarDTO> content = carList.stream().map(mapper::toDTO).collect(Collectors.toList());
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
        List<CarDTO> content = carList.stream().map(mapper::toDTO).collect(Collectors.toList());
        return PageableResponse.builder()
                .content(content)
                .pageNo(cars.getNumber())
                .pageSize(cars.getSize())
                .totalPages(cars.getTotalPages())
                .totalNumberOfElements(cars.getNumberOfElements())
                .isLast(cars.isLast()).build();
    }
}
