package co.edu.sena.service.impl;

import co.edu.sena.domain.Dats;
import co.edu.sena.repository.DatsRepository;
import co.edu.sena.service.DatsService;
import co.edu.sena.service.dto.DatsDTO;
import co.edu.sena.service.mapper.DatsMapper;
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
 * Service Implementation for managing {@link Dats}.
 */
@Service
@Transactional
public class DatsServiceImpl implements DatsService {

    private final Logger log = LoggerFactory.getLogger(DatsServiceImpl.class);

    private final DatsRepository datsRepository;

    private final DatsMapper datsMapper;

    public DatsServiceImpl(DatsRepository datsRepository, DatsMapper datsMapper) {
        this.datsRepository = datsRepository;
        this.datsMapper = datsMapper;
    }

    @Override
    public DatsDTO save(DatsDTO datsDTO) {
        log.debug("Request to save Dats : {}", datsDTO);
        Dats dats = datsMapper.toEntity(datsDTO);
        dats = datsRepository.save(dats);
        return datsMapper.toDto(dats);
    }

    @Override
    public DatsDTO update(DatsDTO datsDTO) {
        log.debug("Request to save Dats : {}", datsDTO);
        Dats dats = datsMapper.toEntity(datsDTO);
        dats = datsRepository.save(dats);
        return datsMapper.toDto(dats);
    }

    @Override
    public Optional<DatsDTO> partialUpdate(DatsDTO datsDTO) {
        log.debug("Request to partially update Dats : {}", datsDTO);

        return datsRepository
            .findById(datsDTO.getId())
            .map(existingDats -> {
                datsMapper.partialUpdate(existingDats, datsDTO);

                return existingDats;
            })
            .map(datsRepository::save)
            .map(datsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dats");
        return datsRepository.findAll(pageable).map(datsMapper::toDto);
    }

    /**
     *  Get all the dats where DocumenType is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DatsDTO> findAllWhereDocumenTypeIsNull() {
        log.debug("Request to get all dats where DocumenType is null");
        return StreamSupport
            .stream(datsRepository.findAll().spliterator(), false)
            .filter(dats -> dats.getDocumenType() == null)
            .map(datsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DatsDTO> findOne(Long id) {
        log.debug("Request to get Dats : {}", id);
        return datsRepository.findById(id).map(datsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dats : {}", id);
        datsRepository.deleteById(id);
    }
}
