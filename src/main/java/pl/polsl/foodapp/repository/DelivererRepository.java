package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.foodapp.entity.Deliverer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DelivererRepository extends JpaRepository<Deliverer, Long> {
    Optional<Deliverer> findByUuid(UUID uuid);
}
