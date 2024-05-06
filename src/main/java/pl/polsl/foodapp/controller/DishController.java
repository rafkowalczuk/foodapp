package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.dto.DishDTO;
import pl.polsl.foodapp.repository.DishRepository;
import pl.polsl.foodapp.service.DishServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    private final DishServiceImpl dishService;

    public DishController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }


    @GetMapping
    public List<DishDTO> get() {
        return dishService.getAll();
    }

    @GetMapping("/{uuid}")
    public DishDTO get(@PathVariable UUID uuid) {
        return dishService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DishDTO json) {
        dishService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        dishService.delete(uuid);
    }

}
