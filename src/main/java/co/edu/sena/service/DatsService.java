package co.edu.sena.service;

import co.edu.sena.service.dto.DatsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Dats}.
 */
public interface DatsService {
    /**
     * Save a dats.
     *
     * @param datsDTO the entity to save.
     * @return the persisted entity.
     */
    DatsDTO save(DatsDTO datsDTO);

    /**
     * Updates a dats.
     *
     * @param datsDTO the entity to update.
     * @return the persisted entity.
     */
    DatsDTO update(DatsDTO datsDTO);

    /**
     * Partially updates a dats.
     *
     * @param datsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DatsDTO> partialUpdate(DatsDTO datsDTO);

    /**
     * Get all the dats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DatsDTO> findAll(Pageable pageable);
    /**
     * Get all the DatsDTO where DocumenType is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DatsDTO> findAllWhereDocumenTypeIsNull();

    /**
     * Get the "id" dats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DatsDTO> findOne(Long id);

    /**
     * Delete the "id" dats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
