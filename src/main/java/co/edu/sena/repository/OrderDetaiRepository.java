package co.edu.sena.repository;

import co.edu.sena.domain.OrderDetai;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderDetai entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetaiRepository extends JpaRepository<OrderDetai, Long> {}
