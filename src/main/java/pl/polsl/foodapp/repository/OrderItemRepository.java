package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.OrderItem;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByUuid(UUID uuid);
}
