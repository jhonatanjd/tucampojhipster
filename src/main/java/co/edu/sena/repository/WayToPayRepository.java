package co.edu.sena.repository;

import co.edu.sena.domain.WayToPay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WayToPay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WayToPayRepository extends JpaRepository<WayToPay, Long> {}
