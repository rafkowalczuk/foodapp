package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.MenuItemDTO;
import pl.polsl.foodapp.dto.RestaurantDTO;
import pl.polsl.foodapp.service.RestaurantServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/restaurants")
public class RestaurantController {

    private final RestaurantServiceImpl restaurantService;

    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantDTO> get() {
        return restaurantService.getAll();
    }

    @GetMapping("/{uuid}")
    public RestaurantDTO get(@PathVariable UUID uuid) {
        return restaurantService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid RestaurantDTO json) {
        restaurantService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        restaurantService.delete(uuid);
    }
}
