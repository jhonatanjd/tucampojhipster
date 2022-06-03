package co.edu.sena.service.impl;

import co.edu.sena.domain.Driver;
import co.edu.sena.repository.DriverRepository;
import co.edu.sena.service.DriverService;
import co.edu.sena.service.dto.DriverDTO;
import co.edu.sena.service.mapper.DriverMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Driver}.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @Override
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = driverMapper.toEntity(driverDTO);
        driver = driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDTO update(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = driverMapper.toEntity(driverDTO);
        // no save call needed as we have no fields that can be updated
        return driverMapper.toDto(driver);
    }

    @Override
    public Optional<DriverDTO> partialUpdate(DriverDTO driverDTO) {
        log.debug("Request to partially update Driver : {}", driverDTO);

        return driverRepository
            .findById(driverDTO.getId())
            .map(existingDriver -> {
                driverMapper.partialUpdate(existingDriver, driverDTO);

                return existingDriver;
            })
            // .map(driverRepository::save)
            .map(driverMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DriverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        return driverRepository.findAll(pageable).map(driverMapper::toDto);
    }

    /**
     *  Get all the drivers where Vehicle is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DriverDTO> findAllWhereVehicleIsNull() {
        log.debug("Request to get all drivers where Vehicle is null");
        return StreamSupport
            .stream(driverRepository.findAll().spliterator(), false)
            .filter(driver -> driver.getVehicle() == null)
            .map(driverMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DriverDTO> findOne(Long id) {
        log.debug("Request to get Driver : {}", id);
        return driverRepository.findById(id).map(driverMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.deleteById(id);
    }
}
