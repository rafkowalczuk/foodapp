package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.dto.DiscountCodeDTO;
import pl.polsl.foodapp.repository.DiscountCodeRepository;
import pl.polsl.foodapp.service.DiscountCodeServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/discount-codes",produces = MediaType.APPLICATION_JSON_VALUE)
public class DiscountCodeController {

    private final DiscountCodeServiceImpl discountCodeService;

    public DiscountCodeController(DiscountCodeServiceImpl discountCodeService) {
        this.discountCodeService = discountCodeService;
    }


    @GetMapping
    public List<DiscountCodeDTO> get() {
        return discountCodeService.getAll();
    }


    @GetMapping("/{uuid}")
    public DiscountCodeDTO get(@PathVariable UUID uuid) {
        return discountCodeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DiscountCodeDTO json) {
        discountCodeService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        discountCodeService.delete(uuid);
    }
}
