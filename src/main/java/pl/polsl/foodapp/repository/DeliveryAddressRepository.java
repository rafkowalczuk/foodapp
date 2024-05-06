package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.DeliveryAddress;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    Optional<DeliveryAddress> findByUuid(UUID uuid);
}
