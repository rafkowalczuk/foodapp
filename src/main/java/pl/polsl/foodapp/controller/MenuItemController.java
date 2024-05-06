package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.dto.MenuItemDTO;
import pl.polsl.foodapp.repository.MenuItemRepository;
import pl.polsl.foodapp.service.MenuItemServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/menu-items", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemController {

    private final MenuItemServiceImpl menuItemService;

    public MenuItemController(MenuItemServiceImpl menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuItemDTO> get() {
        return menuItemService.getAll();
    }


    @GetMapping("/{uuid}")
    public MenuItemDTO get(@PathVariable UUID uuid) {
        return menuItemService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid MenuItemDTO json) {
        menuItemService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        menuItemService.delete(uuid);
    }


}
