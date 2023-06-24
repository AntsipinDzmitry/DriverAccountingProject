package com.example.driveraccountingproject.service;

import com.example.driveraccountingproject.dto.DriverAccountDTO;
import com.example.driveraccountingproject.dto.DriverDTO;
import com.example.driveraccountingproject.dto.PageableResponse;
import com.example.driveraccountingproject.exception.DriverNotFoundException;
import com.example.driveraccountingproject.exception.InsufficientAccountFundsException;
import com.example.driveraccountingproject.mapper.AccountMapper;
import com.example.driveraccountingproject.mapper.DriverMapper;
import com.example.driveraccountingproject.model.Driver;
import com.example.driveraccountingproject.model.DriverAccount;
import com.example.driveraccountingproject.model.fieldenum.Currency;
import com.example.driveraccountingproject.repository.DriverAccountRepository;
import com.example.driveraccountingproject.repository.DriverRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverAccountRepository driverAccountRepository;
    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private final DriverMapper driverMapper = Mappers.getMapper(DriverMapper.class);

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, DriverAccountRepository driverAccountRepository) {
        this.driverRepository = driverRepository;
        this.driverAccountRepository = driverAccountRepository;
    }

    @Override
    public DriverDTO createDriver(DriverDTO driverDTO) {
        Driver newDriver = driverRepository.save(driverMapper.toEntity(driverDTO));
        DriverAccount driverAccount =
                driverAccountRepository.save(DriverAccount.builder()
                        .driver(newDriver)
                        .build());
        newDriver.setAccount(driverAccount);
        return driverMapper.toDTO(newDriver);
    }

    @Override
    public DriverDTO getDriverById(Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(() ->
                new DriverNotFoundException("Driver not found"));
        return driverMapper.toDTO(driver);
    }

    @Override
    public void congratulateDriverOnBirthday(Long driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() ->
                new DriverNotFoundException("Driver not found"));
        if (driver != null && isDriverBirthday(driver)) {
            logger.info("Happy birthday, " + driver.getFullName() + "!");
        }
    }

    private boolean isDriverBirthday(Driver driver) {
        LocalDate currentDate = LocalDate.now();
        LocalDate driverBirthday = LocalDate.of(currentDate.getYear(), driver.getDateOfBirth().getMonth(), driver.getDateOfBirth().getDayOfMonth());
        return currentDate.equals(driverBirthday);
    }

    @Override
    public DriverDTO updateDriver(DriverDTO driverDTO) {
        Driver newDriver = driverRepository.findById(driverDTO.getId()).orElseThrow(() ->
                new DriverNotFoundException("Driver not found"));
        newDriver.setFullName(driverDTO.getFullName());
        newDriver.setPassport(driverDTO.getPassport());
        newDriver.setDriverLicenseCategory(driverDTO.getDriverLicenseCategory());
        newDriver.setDateOfBirth(driverDTO.getDateOfBirth());
        newDriver.setExperience(driverDTO.getExperience());
        newDriver.setUpdated(LocalDateTime.now());
        Driver updatedDriver = driverRepository.save(newDriver);
        return driverMapper.toDTO(updatedDriver);
    }

    @Override
    public PageableResponse getAllDrivers(int pageNo, int pageSize, String sortBy) {

        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Driver> drivers = driverRepository.findAll(pageable);
        List<Driver> driverList = drivers.getContent();
        List<DriverDTO> content = driverList.stream().map(driverMapper::toDTO).collect(Collectors.toList());
        return PageableResponse.builder()
                .content(content)
                .pageNo(drivers.getNumber())
                .pageSize(drivers.getSize())
                .totalPages(drivers.getTotalPages())
                .totalNumberOfElements(drivers.getNumberOfElements())
                .isLast(drivers.isLast())
                .build();
    }

    @Override
    public void deleteDriver(Long driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(() ->
                new DriverNotFoundException("Driver not found"));
        driverRepository.delete(driver);
    }

    @Override
    public DriverAccountDTO addFunds(Long driverId, BigDecimal amount, Currency currency) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverAccount account = driver.getAccount();
        BigDecimal convertedAmount = convertCurrency(amount, currency, account.getCurrency());
        BigDecimal newBalance = account.getBalance().add(convertedAmount);
        account.setBalance(newBalance);

        DriverAccount updatedAccount = driverRepository.save(driver).getAccount();
        return accountMapper.toDTO(updatedAccount);
    }

    @Override
    public DriverAccountDTO withdrawFunds(Long driverId, BigDecimal amount, Currency currency) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverAccount account = driver.getAccount();
        BigDecimal convertedAmount = convertCurrency(amount, currency, account.getCurrency());
        BigDecimal newBalance = account.getBalance().subtract(convertedAmount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientAccountFundsException("Insufficient funds");
        }
        account.setBalance(newBalance);

        DriverAccount updatedAccount = driverRepository.save(driver).getAccount();
        return accountMapper.toDTO(updatedAccount);
    }

    @Override
    public DriverAccountDTO getAccountStatus(Long driverId, Currency currency) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverAccount account = driver.getAccount();
        BigDecimal convertedBalance = convertCurrency(account.getBalance(), account.getCurrency(), currency);

        return new DriverAccountDTO(currency, convertedBalance);
    }

    @Override
    public BigDecimal convertCurrency(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == toCurrency) {
            return amount;
        }
        BigDecimal conversionRate = getConversionRate(fromCurrency, toCurrency);
        return amount.multiply(conversionRate);
    }

    @Override
    public BigDecimal getConversionRate(Currency fromCurrency, Currency toCurrency) {
        Map<Pair<Currency, Currency>, BigDecimal> conversionRates = new HashMap<>();
        conversionRates.put(new ImmutablePair<>(Currency.RED_DOLLAR, Currency.GREEN_DOLLAR), new BigDecimal("2.5"));
        conversionRates.put(new ImmutablePair<>(Currency.GREEN_DOLLAR, Currency.BLUE_DOLLAR), new BigDecimal("0.6"));
        conversionRates.put(new ImmutablePair<>(Currency.BLUE_DOLLAR, Currency.GREEN_DOLLAR), new BigDecimal("1.67"));
        conversionRates.put(new ImmutablePair<>(Currency.GREEN_DOLLAR, Currency.RED_DOLLAR), new BigDecimal("0.4"));
        conversionRates.put(new ImmutablePair<>(Currency.BLUE_DOLLAR, Currency.RED_DOLLAR), new BigDecimal("0.8"));
        conversionRates.put(new ImmutablePair<>(Currency.RED_DOLLAR, Currency.BLUE_DOLLAR), new BigDecimal("1.25"));

        Pair<Currency, Currency> currencyPair = new ImmutablePair<>(fromCurrency, toCurrency);
        if (conversionRates.containsKey(currencyPair)) {
            return conversionRates.get(currencyPair);
        } else if (fromCurrency == toCurrency) {
            return BigDecimal.ONE;
        } else {
            throw new UnsupportedOperationException("Conversion rate not available");
        }
    }

}
