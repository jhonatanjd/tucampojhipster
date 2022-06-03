package co.edu.sena.repository;

import co.edu.sena.domain.Units;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Units entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitsRepository extends JpaRepository<Units, Long> {}
