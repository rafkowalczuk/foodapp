package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DishDTO;
import pl.polsl.foodapp.dto.EmployeeDTO;
import pl.polsl.foodapp.dto.MenuItemDTO;
import pl.polsl.foodapp.entity.*;
import pl.polsl.foodapp.repository.DishRepository;
import pl.polsl.foodapp.repository.MenuItemRepository;
import pl.polsl.foodapp.repository.RestaurantRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public List<MenuItemDTO> getAll() {
        return menuItemRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    public void put(UUID uuid, MenuItemDTO menuItemDTO) {
        if (!Objects.equal(menuItemDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = restaurantRepository.findByUuid(menuItemDTO.getRestaurant().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Dish> dishes = new ArrayList<>();
        for (DishDTO d : menuItemDTO.getDishes()) {
            Dish dish = dishRepository.findByUuid(d.getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            dishes.add(dish);
        }

        MenuItem menuItem = menuItemRepository.findByUuid(menuItemDTO.getUuid())
                .orElseGet(() -> newMenuItem(uuid, restaurant));

        menuItem.setName(menuItemDTO.getName());
        menuItem.setNettoPrice(menuItemDTO.getNettoPrice());
        menuItem.setVaxTax(menuItemDTO.getVatTax());
        menuItem.setBruttoPrice(menuItemDTO.getBruttoPrice());
        menuItem.setDishes(dishes);

        if (menuItem.getId() == null) {
            menuItemRepository.save(menuItem);
        }
    }

    public void delete(UUID uuid) {
        MenuItem menuItem = menuItemRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        menuItemRepository.delete(menuItem);
    }

    public Optional<MenuItemDTO> getByUuid(UUID uuid) {
        return menuItemRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private MenuItem newMenuItem(UUID uuid, Restaurant restaurant) {
        return new MenuItemBuilder()
                .withUuid(uuid)
                .withRestaurant(restaurant)
                .build();
    }


}
