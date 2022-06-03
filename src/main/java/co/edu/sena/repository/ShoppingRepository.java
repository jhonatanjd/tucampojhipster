package co.edu.sena.repository;

import co.edu.sena.domain.Shopping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Shopping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, Long> {}
