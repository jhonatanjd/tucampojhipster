package co.edu.sena.service;

import co.edu.sena.service.dto.ShoppingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Shopping}.
 */
public interface ShoppingService {
    /**
     * Save a shopping.
     *
     * @param shoppingDTO the entity to save.
     * @return the persisted entity.
     */
    ShoppingDTO save(ShoppingDTO shoppingDTO);

    /**
     * Updates a shopping.
     *
     * @param shoppingDTO the entity to update.
     * @return the persisted entity.
     */
    ShoppingDTO update(ShoppingDTO shoppingDTO);

    /**
     * Partially updates a shopping.
     *
     * @param shoppingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoppingDTO> partialUpdate(ShoppingDTO shoppingDTO);

    /**
     * Get all the shoppings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShoppingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shopping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoppingDTO> findOne(Long id);

    /**
     * Delete the "id" shopping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
