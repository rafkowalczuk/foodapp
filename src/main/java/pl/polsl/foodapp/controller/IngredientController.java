package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.dto.IngredientDTO;
import pl.polsl.foodapp.repository.IngredientRepository;
import pl.polsl.foodapp.service.IngredientServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {


    private final IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping
    public List<IngredientDTO> get() {
        return ingredientService.getAll();
    }


    @GetMapping("/{uuid}")
    public IngredientDTO get(@PathVariable UUID uuid) {
        return ingredientService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid IngredientDTO json) {
        ingredientService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        ingredientService.delete(uuid);
    }
}
