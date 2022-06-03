package co.edu.sena.service.impl;

import co.edu.sena.domain.Shopping;
import co.edu.sena.repository.ShoppingRepository;
import co.edu.sena.service.ShoppingService;
import co.edu.sena.service.dto.ShoppingDTO;
import co.edu.sena.service.mapper.ShoppingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Shopping}.
 */
@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingService {

    private final Logger log = LoggerFactory.getLogger(ShoppingServiceImpl.class);

    private final ShoppingRepository shoppingRepository;

    private final ShoppingMapper shoppingMapper;

    public ShoppingServiceImpl(ShoppingRepository shoppingRepository, ShoppingMapper shoppingMapper) {
        this.shoppingRepository = shoppingRepository;
        this.shoppingMapper = shoppingMapper;
    }

    @Override
    public ShoppingDTO save(ShoppingDTO shoppingDTO) {
        log.debug("Request to save Shopping : {}", shoppingDTO);
        Shopping shopping = shoppingMapper.toEntity(shoppingDTO);
        shopping = shoppingRepository.save(shopping);
        return shoppingMapper.toDto(shopping);
    }

    @Override
    public ShoppingDTO update(ShoppingDTO shoppingDTO) {
        log.debug("Request to save Shopping : {}", shoppingDTO);
        Shopping shopping = shoppingMapper.toEntity(shoppingDTO);
        shopping = shoppingRepository.save(shopping);
        return shoppingMapper.toDto(shopping);
    }

    @Override
    public Optional<ShoppingDTO> partialUpdate(ShoppingDTO shoppingDTO) {
        log.debug("Request to partially update Shopping : {}", shoppingDTO);

        return shoppingRepository
            .findById(shoppingDTO.getId())
            .map(existingShopping -> {
                shoppingMapper.partialUpdate(existingShopping, shoppingDTO);

                return existingShopping;
            })
            .map(shoppingRepository::save)
            .map(shoppingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoppingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shoppings");
        return shoppingRepository.findAll(pageable).map(shoppingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingDTO> findOne(Long id) {
        log.debug("Request to get Shopping : {}", id);
        return shoppingRepository.findById(id).map(shoppingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shopping : {}", id);
        shoppingRepository.deleteById(id);
    }
}
