package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.MenuItem;

import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Optional<MenuItem> findByUuid(UUID uuid);

}
