package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.OpenTime;

import java.util.Optional;
import java.util.UUID;

public interface OpenTimeRepository extends JpaRepository<OpenTime, Long> {
    Optional<OpenTime> findByUuid(UUID uuid);
}
