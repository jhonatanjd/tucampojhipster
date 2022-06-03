package co.edu.sena.service.impl;

import co.edu.sena.domain.Anonymous;
import co.edu.sena.repository.AnonymousRepository;
import co.edu.sena.service.AnonymousService;
import co.edu.sena.service.dto.AnonymousDTO;
import co.edu.sena.service.mapper.AnonymousMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Anonymous}.
 */
@Service
@Transactional
public class AnonymousServiceImpl implements AnonymousService {

    private final Logger log = LoggerFactory.getLogger(AnonymousServiceImpl.class);

    private final AnonymousRepository anonymousRepository;

    private final AnonymousMapper anonymousMapper;

    public AnonymousServiceImpl(AnonymousRepository anonymousRepository, AnonymousMapper anonymousMapper) {
        this.anonymousRepository = anonymousRepository;
        this.anonymousMapper = anonymousMapper;
    }

    @Override
    public AnonymousDTO save(AnonymousDTO anonymousDTO) {
        log.debug("Request to save Anonymous : {}", anonymousDTO);
        Anonymous anonymous = anonymousMapper.toEntity(anonymousDTO);
        anonymous = anonymousRepository.save(anonymous);
        return anonymousMapper.toDto(anonymous);
    }

    @Override
    public AnonymousDTO update(AnonymousDTO anonymousDTO) {
        log.debug("Request to save Anonymous : {}", anonymousDTO);
        Anonymous anonymous = anonymousMapper.toEntity(anonymousDTO);
        anonymous = anonymousRepository.save(anonymous);
        return anonymousMapper.toDto(anonymous);
    }

    @Override
    public Optional<AnonymousDTO> partialUpdate(AnonymousDTO anonymousDTO) {
        log.debug("Request to partially update Anonymous : {}", anonymousDTO);

        return anonymousRepository
            .findById(anonymousDTO.getId())
            .map(existingAnonymous -> {
                anonymousMapper.partialUpdate(existingAnonymous, anonymousDTO);

                return existingAnonymous;
            })
            .map(anonymousRepository::save)
            .map(anonymousMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnonymousDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Anonymous");
        return anonymousRepository.findAll(pageable).map(anonymousMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnonymousDTO> findOne(Long id) {
        log.debug("Request to get Anonymous : {}", id);
        return anonymousRepository.findById(id).map(anonymousMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anonymous : {}", id);
        anonymousRepository.deleteById(id);
    }
}
