package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUuid(UUID uuid);
}
