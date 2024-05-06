package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.dto.DiscountCodeDTO;
import pl.polsl.foodapp.dto.RestaurantDTO;
import pl.polsl.foodapp.dto.UserDTO;
import pl.polsl.foodapp.entity.*;
import pl.polsl.foodapp.repository.DiscountCodeRepository;
import pl.polsl.foodapp.repository.RestaurantRepository;
import pl.polsl.foodapp.repository.UserRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.polsl.foodapp.utils.ConverterUtils.convert;

@Service
public class DiscountCodeServiceImpl {

    private final DiscountCodeRepository discountCodeRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public DiscountCodeServiceImpl(DiscountCodeRepository discountCodeRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.discountCodeRepository = discountCodeRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public List<DiscountCodeDTO> getAll() {
        return discountCodeRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    public void put(UUID uuid, DiscountCodeDTO discountCodeDTO) {
        if (!Objects.equal(discountCodeDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<User> users = new ArrayList<>();
        if (discountCodeDTO.getUsers() != null) {
            for (UserDTO userDTO : discountCodeDTO.getUsers()) {
                User user = userRepository.findByUuid(userDTO.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
                users.add(user);
            }
        }

        List<Restaurant> restaurants = new ArrayList<>();
        if (discountCodeDTO.getRestaurants() != null) {
            for (RestaurantDTO restaurantDTO : discountCodeDTO.getRestaurants()) {
                Restaurant restaurant = restaurantRepository.findByUuid(restaurantDTO.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
                restaurants.add(restaurant);
            }
        }

        DiscountCode discountCode = discountCodeRepository.findByUuid(discountCodeDTO.getUuid())
                .orElseGet(() -> newDiscountCode(uuid));

        discountCode.setCode(discountCodeDTO.getCode());
        discountCode.setDiscount(discountCodeDTO.getDiscount());
        discountCode.setDiscountUnit(discountCodeDTO.getDiscountUnit());
        discountCode.setPeriod(convert(discountCodeDTO.getPeriod()));
        discountCode.setUsers(users);
        discountCode.setRestaurants(restaurants);

        if (discountCode.getId() == null) {
            discountCodeRepository.save(discountCode);
        }}

    public void delete(UUID uuid) {
        DiscountCode discountCode = discountCodeRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        discountCodeRepository.delete(discountCode);
    }

    public Optional<DiscountCodeDTO> getByUuid(UUID uuid) {
        return discountCodeRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private DiscountCode newDiscountCode(UUID uuid) {
        return new DiscountCodeBuilder()
                .withUuid(uuid)
                .build();
    }
}
