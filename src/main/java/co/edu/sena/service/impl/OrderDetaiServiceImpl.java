package co.edu.sena.service.impl;

import co.edu.sena.domain.OrderDetai;
import co.edu.sena.repository.OrderDetaiRepository;
import co.edu.sena.service.OrderDetaiService;
import co.edu.sena.service.dto.OrderDetaiDTO;
import co.edu.sena.service.mapper.OrderDetaiMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderDetai}.
 */
@Service
@Transactional
public class OrderDetaiServiceImpl implements OrderDetaiService {

    private final Logger log = LoggerFactory.getLogger(OrderDetaiServiceImpl.class);

    private final OrderDetaiRepository orderDetaiRepository;

    private final OrderDetaiMapper orderDetaiMapper;

    public OrderDetaiServiceImpl(OrderDetaiRepository orderDetaiRepository, OrderDetaiMapper orderDetaiMapper) {
        this.orderDetaiRepository = orderDetaiRepository;
        this.orderDetaiMapper = orderDetaiMapper;
    }

    @Override
    public OrderDetaiDTO save(OrderDetaiDTO orderDetaiDTO) {
        log.debug("Request to save OrderDetai : {}", orderDetaiDTO);
        OrderDetai orderDetai = orderDetaiMapper.toEntity(orderDetaiDTO);
        orderDetai = orderDetaiRepository.save(orderDetai);
        return orderDetaiMapper.toDto(orderDetai);
    }

    @Override
    public OrderDetaiDTO update(OrderDetaiDTO orderDetaiDTO) {
        log.debug("Request to save OrderDetai : {}", orderDetaiDTO);
        OrderDetai orderDetai = orderDetaiMapper.toEntity(orderDetaiDTO);
        // no save call needed as we have no fields that can be updated
        return orderDetaiMapper.toDto(orderDetai);
    }

    @Override
    public Optional<OrderDetaiDTO> partialUpdate(OrderDetaiDTO orderDetaiDTO) {
        log.debug("Request to partially update OrderDetai : {}", orderDetaiDTO);

        return orderDetaiRepository
            .findById(orderDetaiDTO.getId())
            .map(existingOrderDetai -> {
                orderDetaiMapper.partialUpdate(existingOrderDetai, orderDetaiDTO);

                return existingOrderDetai;
            })
            // .map(orderDetaiRepository::save)
            .map(orderDetaiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetaiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderDetais");
        return orderDetaiRepository.findAll(pageable).map(orderDetaiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetaiDTO> findOne(Long id) {
        log.debug("Request to get OrderDetai : {}", id);
        return orderDetaiRepository.findById(id).map(orderDetaiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetai : {}", id);
        orderDetaiRepository.deleteById(id);
    }
}
