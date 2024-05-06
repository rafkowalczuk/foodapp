package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DelivererDTO;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.repository.DeliveryAddressRepository;
import pl.polsl.foodapp.service.DeliveryAddressServiceImpl;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping(path = "/api/delivery-address", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryAddressController {

   private final DeliveryAddressServiceImpl deliveryAddressService;

    public DeliveryAddressController(DeliveryAddressServiceImpl deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }




    @GetMapping
    public List<DeliveryAddressDTO> get() {
        return deliveryAddressService.getAll();
    }


    @GetMapping("/{uuid}")
    public DeliveryAddressDTO get(@PathVariable UUID uuid) {
        return deliveryAddressService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DeliveryAddressDTO json) {
        deliveryAddressService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        deliveryAddressService.delete(uuid);
    }
}

