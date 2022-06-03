package co.edu.sena.repository;

import co.edu.sena.domain.DocumenType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DocumenType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumenTypeRepository extends JpaRepository<DocumenType, Long> {}
