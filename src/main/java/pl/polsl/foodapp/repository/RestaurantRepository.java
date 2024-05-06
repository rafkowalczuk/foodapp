package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByUuid(UUID uuid);

}
