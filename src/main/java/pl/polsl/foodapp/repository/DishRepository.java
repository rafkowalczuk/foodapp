package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.Dish;

import java.util.Optional;
import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findByUuid(UUID uuid);
}
