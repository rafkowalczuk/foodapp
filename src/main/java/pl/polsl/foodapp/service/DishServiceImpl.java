package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DiscountCodeDTO;
import pl.polsl.foodapp.dto.DishDTO;
import pl.polsl.foodapp.dto.MenuItemDTO;
import pl.polsl.foodapp.entity.Dish;
import pl.polsl.foodapp.entity.DishBuilder;
import pl.polsl.foodapp.entity.MenuItem;
import pl.polsl.foodapp.entity.Product;
import pl.polsl.foodapp.repository.DishRepository;
import pl.polsl.foodapp.repository.MenuItemRepository;
import pl.polsl.foodapp.repository.ProductRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl {
    private final DishRepository dishRepository;
    private final MenuItemRepository menuItemRepository;
    private final ProductRepository productRepository;

    public DishServiceImpl(DishRepository dishRepository, MenuItemRepository menuItemRepository, ProductRepository productRepository) {
        this.dishRepository = dishRepository;
        this.menuItemRepository = menuItemRepository;
        this.productRepository = productRepository;
    }

    public List<DishDTO> getAll() {
        return dishRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    public void put(UUID uuid, DishDTO dishDTO) {
        if (!Objects.equal(dishDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.findByUuid(dishDTO.getProduct().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<MenuItem> menuItems = new ArrayList<>();
        if (dishDTO.getMenuItems() != null) {
            for (MenuItemDTO d : dishDTO.getMenuItems()) {
                MenuItem menuItem = menuItemRepository.findByUuid(d.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                menuItems.add(menuItem);
            }
        }

        Dish dish = dishRepository.findByUuid(dishDTO.getUuid())
                .orElseGet(() -> newDish(uuid));

        dish.setQuantity(dishDTO.getQuantity());
        dish.setProduct(product);
        dish.setMenuItem(menuItems);

        if (dish.getId() == null) {
            dishRepository.save(dish);
        }
    }

    public void delete(UUID uuid) {
        Dish dish = dishRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        dishRepository.delete(dish);
    }

    public Optional<DishDTO> getByUuid(UUID uuid) {
        return dishRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Dish newDish(UUID uuid) {
        return new DishBuilder()
                .withUuid(uuid)
                .build();
    }

}
