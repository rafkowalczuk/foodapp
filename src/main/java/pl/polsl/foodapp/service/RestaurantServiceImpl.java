package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.RestaurantDTO;
import pl.polsl.foodapp.entity.Restaurant;
import pl.polsl.foodapp.entity.RestaurantBuilder;
import pl.polsl.foodapp.repository.RestaurantRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.polsl.foodapp.utils.ConverterUtils.*;

@Service
public class RestaurantServiceImpl {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    public List<RestaurantDTO> getAll() {
        return restaurantRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }


    public void put(UUID uuid, RestaurantDTO restaurantDTO) {
        if (!Objects.equal(restaurantDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Restaurant restaurant = restaurantRepository.findByUuid(restaurantDTO.getUuid())
                .orElseGet(() -> newRestaurant(uuid));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setLogginData(convert(restaurantDTO.getLogginData()));
        restaurant.setCompanyData(convert(restaurantDTO.getCompanyData()));
        restaurant.setOpenTimes(convertOpenTimeDTOS(restaurantDTO.getOpenTimeDTOS()));
        restaurant.setMenuItems(convertMenuItemDTOS(restaurantDTO.getMenuItemDTOS()));
        restaurant.setDiscountCodes(convertDiscountCodeDTOS(restaurantDTO.getDiscountCodes()));
        restaurant.setArchive(restaurantDTO.getArchive());

        if (restaurant.getId() == null) {
            restaurantRepository.save(restaurant);
        }
    }


    public void delete(UUID uuid) {
        Restaurant restaurant = restaurantRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        restaurantRepository.delete(restaurant);
    }

    public Optional<RestaurantDTO> getByUuid(UUID uuid) {
        return restaurantRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Restaurant newRestaurant(UUID uuid) {
        return new RestaurantBuilder()
                .withUuid(uuid)
                .build();
    }

}
