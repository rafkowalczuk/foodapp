package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.IngredientDTO;
import pl.polsl.foodapp.dto.ProductDTO;
import pl.polsl.foodapp.entity.Dish;
import pl.polsl.foodapp.entity.Ingredient;
import pl.polsl.foodapp.entity.Product;
import pl.polsl.foodapp.entity.ProductBuilder;
import pl.polsl.foodapp.repository.DishRepository;
import pl.polsl.foodapp.repository.IngredientRepository;
import pl.polsl.foodapp.repository.ProductRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final DishRepository dishRepository;

    public ProductServiceImpl(ProductRepository productRepository, IngredientRepository ingredientRepository, DishRepository dishRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.dishRepository = dishRepository;
    }



    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }


    public void put(UUID uuid, ProductDTO productDTO) {
        if (!Objects.equal(productDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDTO p : productDTO.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findByUuid(p.getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            ingredients.add(ingredient);
        }

        Product product = productRepository.findByUuid(productDTO.getUuid())
                .orElseGet(() -> newProduct(uuid));

        product.setName(productDTO.getName());
        product.setIngredients(ingredients);

        if (productDTO.getDish() != null) {
            Dish dish = dishRepository.findByUuid(productDTO.getDish().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            product.setDish(dish);
        } else {
            product.setDish(null);
        }

        if (product.getId() == null) {
            productRepository.save(product);
        }
    }


    public void delete(UUID uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        productRepository.delete(product);
    }


    public Optional<ProductDTO> getByUuid(UUID uuid) {
        return productRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Product newProduct(UUID uuid) {
        return new ProductBuilder()
                .withUuid(uuid)
                .build();
    }
}
