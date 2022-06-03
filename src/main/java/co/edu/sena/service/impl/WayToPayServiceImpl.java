package co.edu.sena.service.impl;

import co.edu.sena.domain.WayToPay;
import co.edu.sena.repository.WayToPayRepository;
import co.edu.sena.service.WayToPayService;
import co.edu.sena.service.dto.WayToPayDTO;
import co.edu.sena.service.mapper.WayToPayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WayToPay}.
 */
@Service
@Transactional
public class WayToPayServiceImpl implements WayToPayService {

    private final Logger log = LoggerFactory.getLogger(WayToPayServiceImpl.class);

    private final WayToPayRepository wayToPayRepository;

    private final WayToPayMapper wayToPayMapper;

    public WayToPayServiceImpl(WayToPayRepository wayToPayRepository, WayToPayMapper wayToPayMapper) {
        this.wayToPayRepository = wayToPayRepository;
        this.wayToPayMapper = wayToPayMapper;
    }

    @Override
    public WayToPayDTO save(WayToPayDTO wayToPayDTO) {
        log.debug("Request to save WayToPay : {}", wayToPayDTO);
        WayToPay wayToPay = wayToPayMapper.toEntity(wayToPayDTO);
        wayToPay = wayToPayRepository.save(wayToPay);
        return wayToPayMapper.toDto(wayToPay);
    }

    @Override
    public WayToPayDTO update(WayToPayDTO wayToPayDTO) {
        log.debug("Request to save WayToPay : {}", wayToPayDTO);
        WayToPay wayToPay = wayToPayMapper.toEntity(wayToPayDTO);
        wayToPay = wayToPayRepository.save(wayToPay);
        return wayToPayMapper.toDto(wayToPay);
    }

    @Override
    public Optional<WayToPayDTO> partialUpdate(WayToPayDTO wayToPayDTO) {
        log.debug("Request to partially update WayToPay : {}", wayToPayDTO);

        return wayToPayRepository
            .findById(wayToPayDTO.getId())
            .map(existingWayToPay -> {
                wayToPayMapper.partialUpdate(existingWayToPay, wayToPayDTO);

                return existingWayToPay;
            })
            .map(wayToPayRepository::save)
            .map(wayToPayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WayToPayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WayToPays");
        return wayToPayRepository.findAll(pageable).map(wayToPayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WayToPayDTO> findOne(Long id) {
        log.debug("Request to get WayToPay : {}", id);
        return wayToPayRepository.findById(id).map(wayToPayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WayToPay : {}", id);
        wayToPayRepository.deleteById(id);
    }
}
