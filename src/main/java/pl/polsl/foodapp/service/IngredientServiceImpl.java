package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.EmployeeDTO;
import pl.polsl.foodapp.dto.IngredientDTO;
import pl.polsl.foodapp.entity.Employee;
import pl.polsl.foodapp.entity.Ingredient;
import pl.polsl.foodapp.entity.IngredientBuilder;
import pl.polsl.foodapp.repository.IngredientRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;

    }

    public List<IngredientDTO> getAll() {
        return ingredientRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    public void put(UUID uuid, IngredientDTO ingredientDTO) {
        if (!Objects.equal(ingredientDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Ingredient ingredient = ingredientRepository.findByUuid(ingredientDTO.getUuid())
                .orElseGet(() -> newIngredient(uuid));

        ingredient.setName(ingredientDTO.getName());
        ingredient.setAllergen(ingredientDTO.getIsAllergen());

        if (ingredient.getId() == null) {
            ingredientRepository.save(ingredient);
        }
    }

    public void delete(UUID uuid) {
        Ingredient ingredient = ingredientRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ingredientRepository.delete(ingredient);
    }

    public Optional<IngredientDTO> getByUuid(UUID uuid) {
        return ingredientRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Ingredient newIngredient(UUID uuid) {
        return new IngredientBuilder()
                .withUuid(uuid)
                .build();
    }
}
