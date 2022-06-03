package co.edu.sena.service.impl;

import co.edu.sena.domain.Administrator;
import co.edu.sena.repository.AdministratorRepository;
import co.edu.sena.service.AdministratorService;
import co.edu.sena.service.dto.AdministratorDTO;
import co.edu.sena.service.mapper.AdministratorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Administrator}.
 */
@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {

    private final Logger log = LoggerFactory.getLogger(AdministratorServiceImpl.class);

    private final AdministratorRepository administratorRepository;

    private final AdministratorMapper administratorMapper;

    public AdministratorServiceImpl(AdministratorRepository administratorRepository, AdministratorMapper administratorMapper) {
        this.administratorRepository = administratorRepository;
        this.administratorMapper = administratorMapper;
    }

    @Override
    public AdministratorDTO save(AdministratorDTO administratorDTO) {
        log.debug("Request to save Administrator : {}", administratorDTO);
        Administrator administrator = administratorMapper.toEntity(administratorDTO);
        administrator = administratorRepository.save(administrator);
        return administratorMapper.toDto(administrator);
    }

    @Override
    public AdministratorDTO update(AdministratorDTO administratorDTO) {
        log.debug("Request to save Administrator : {}", administratorDTO);
        Administrator administrator = administratorMapper.toEntity(administratorDTO);
        // no save call needed as we have no fields that can be updated
        return administratorMapper.toDto(administrator);
    }

    @Override
    public Optional<AdministratorDTO> partialUpdate(AdministratorDTO administratorDTO) {
        log.debug("Request to partially update Administrator : {}", administratorDTO);

        return administratorRepository
            .findById(administratorDTO.getId())
            .map(existingAdministrator -> {
                administratorMapper.partialUpdate(existingAdministrator, administratorDTO);

                return existingAdministrator;
            })
            // .map(administratorRepository::save)
            .map(administratorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdministratorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Administrators");
        return administratorRepository.findAll(pageable).map(administratorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministratorDTO> findOne(Long id) {
        log.debug("Request to get Administrator : {}", id);
        return administratorRepository.findById(id).map(administratorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Administrator : {}", id);
        administratorRepository.deleteById(id);
    }
}
