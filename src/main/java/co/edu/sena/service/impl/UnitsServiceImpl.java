package co.edu.sena.service.impl;

import co.edu.sena.domain.Units;
import co.edu.sena.repository.UnitsRepository;
import co.edu.sena.service.UnitsService;
import co.edu.sena.service.dto.UnitsDTO;
import co.edu.sena.service.mapper.UnitsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Units}.
 */
@Service
@Transactional
public class UnitsServiceImpl implements UnitsService {

    private final Logger log = LoggerFactory.getLogger(UnitsServiceImpl.class);

    private final UnitsRepository unitsRepository;

    private final UnitsMapper unitsMapper;

    public UnitsServiceImpl(UnitsRepository unitsRepository, UnitsMapper unitsMapper) {
        this.unitsRepository = unitsRepository;
        this.unitsMapper = unitsMapper;
    }

    @Override
    public UnitsDTO save(UnitsDTO unitsDTO) {
        log.debug("Request to save Units : {}", unitsDTO);
        Units units = unitsMapper.toEntity(unitsDTO);
        units = unitsRepository.save(units);
        return unitsMapper.toDto(units);
    }

    @Override
    public UnitsDTO update(UnitsDTO unitsDTO) {
        log.debug("Request to save Units : {}", unitsDTO);
        Units units = unitsMapper.toEntity(unitsDTO);
        units = unitsRepository.save(units);
        return unitsMapper.toDto(units);
    }

    @Override
    public Optional<UnitsDTO> partialUpdate(UnitsDTO unitsDTO) {
        log.debug("Request to partially update Units : {}", unitsDTO);

        return unitsRepository
            .findById(unitsDTO.getId())
            .map(existingUnits -> {
                unitsMapper.partialUpdate(existingUnits, unitsDTO);

                return existingUnits;
            })
            .map(unitsRepository::save)
            .map(unitsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnitsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitsRepository.findAll(pageable).map(unitsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UnitsDTO> findOne(Long id) {
        log.debug("Request to get Units : {}", id);
        return unitsRepository.findById(id).map(unitsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Units : {}", id);
        unitsRepository.deleteById(id);
    }
}
