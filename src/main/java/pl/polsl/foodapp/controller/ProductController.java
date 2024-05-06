package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.MenuItemDTO;
import pl.polsl.foodapp.dto.ProductDTO;
import pl.polsl.foodapp.repository.ProductRepository;
import pl.polsl.foodapp.service.ProductServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/products",produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<ProductDTO> get() {
        return productService.getAll();
    }


    @GetMapping("/{uuid}")
    public ProductDTO get(@PathVariable UUID uuid) {
        return productService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid ProductDTO json) {
        productService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        productService.delete(uuid);
    }
}
