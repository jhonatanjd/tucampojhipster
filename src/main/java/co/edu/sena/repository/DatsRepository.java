package co.edu.sena.repository;

import co.edu.sena.domain.Dats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatsRepository extends JpaRepository<Dats, Long> {}
