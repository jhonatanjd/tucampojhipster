package co.edu.sena.repository;

import co.edu.sena.domain.Producer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Producer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {}
