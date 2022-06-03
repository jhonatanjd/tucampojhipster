package co.edu.sena.service.impl;

import co.edu.sena.domain.Producer;
import co.edu.sena.repository.ProducerRepository;
import co.edu.sena.service.ProducerService;
import co.edu.sena.service.dto.ProducerDTO;
import co.edu.sena.service.mapper.ProducerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Producer}.
 */
@Service
@Transactional
public class ProducerServiceImpl implements ProducerService {

    private final Logger log = LoggerFactory.getLogger(ProducerServiceImpl.class);

    private final ProducerRepository producerRepository;

    private final ProducerMapper producerMapper;

    public ProducerServiceImpl(ProducerRepository producerRepository, ProducerMapper producerMapper) {
        this.producerRepository = producerRepository;
        this.producerMapper = producerMapper;
    }

    @Override
    public ProducerDTO save(ProducerDTO producerDTO) {
        log.debug("Request to save Producer : {}", producerDTO);
        Producer producer = producerMapper.toEntity(producerDTO);
        producer = producerRepository.save(producer);
        return producerMapper.toDto(producer);
    }

    @Override
    public ProducerDTO update(ProducerDTO producerDTO) {
        log.debug("Request to save Producer : {}", producerDTO);
        Producer producer = producerMapper.toEntity(producerDTO);
        producer = producerRepository.save(producer);
        return producerMapper.toDto(producer);
    }

    @Override
    public Optional<ProducerDTO> partialUpdate(ProducerDTO producerDTO) {
        log.debug("Request to partially update Producer : {}", producerDTO);

        return producerRepository
            .findById(producerDTO.getId())
            .map(existingProducer -> {
                producerMapper.partialUpdate(existingProducer, producerDTO);

                return existingProducer;
            })
            .map(producerRepository::save)
            .map(producerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProducerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Producers");
        return producerRepository.findAll(pageable).map(producerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProducerDTO> findOne(Long id) {
        log.debug("Request to get Producer : {}", id);
        return producerRepository.findById(id).map(producerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producer : {}", id);
        producerRepository.deleteById(id);
    }
}
