package co.edu.sena.service;

import co.edu.sena.service.dto.ProducerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Producer}.
 */
public interface ProducerService {
    /**
     * Save a producer.
     *
     * @param producerDTO the entity to save.
     * @return the persisted entity.
     */
    ProducerDTO save(ProducerDTO producerDTO);

    /**
     * Updates a producer.
     *
     * @param producerDTO the entity to update.
     * @return the persisted entity.
     */
    ProducerDTO update(ProducerDTO producerDTO);

    /**
     * Partially updates a producer.
     *
     * @param producerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProducerDTO> partialUpdate(ProducerDTO producerDTO);

    /**
     * Get all the producers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProducerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" producer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProducerDTO> findOne(Long id);

    /**
     * Delete the "id" producer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
