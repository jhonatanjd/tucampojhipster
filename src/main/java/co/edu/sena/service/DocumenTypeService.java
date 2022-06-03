package co.edu.sena.service;

import co.edu.sena.service.dto.DocumenTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.DocumenType}.
 */
public interface DocumenTypeService {
    /**
     * Save a documenType.
     *
     * @param documenTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DocumenTypeDTO save(DocumenTypeDTO documenTypeDTO);

    /**
     * Updates a documenType.
     *
     * @param documenTypeDTO the entity to update.
     * @return the persisted entity.
     */
    DocumenTypeDTO update(DocumenTypeDTO documenTypeDTO);

    /**
     * Partially updates a documenType.
     *
     * @param documenTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumenTypeDTO> partialUpdate(DocumenTypeDTO documenTypeDTO);

    /**
     * Get all the documenTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumenTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" documenType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumenTypeDTO> findOne(Long id);

    /**
     * Delete the "id" documenType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
