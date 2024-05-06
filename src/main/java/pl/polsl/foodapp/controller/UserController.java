package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.dto.UserDTO;
import pl.polsl.foodapp.events.OperationEvidenceCreator;
import pl.polsl.foodapp.service.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserServiceImpl userService;
    private final ApplicationEventPublisher eventPublisher;

    public UserController(UserServiceImpl userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public List<UserDTO> get() {
        return userService.getAll();
    }


    @GetMapping("/{uuid}")
    public UserDTO get(@PathVariable UUID uuid) {
        return userService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid UserDTO json) {
        userService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        userService.delete(uuid);
    }

    @Transactional
    @PostMapping("/{uuid}/new-operation")
    public void postOperation(@PathVariable UUID uuid, @RequestBody @Valid UserDTO json) {
        userService.validateNewOperation(uuid, json);

        OperationEvidenceCreator operationEvidenceCreator = new OperationEvidenceCreator(this, json);
        eventPublisher.publishEvent(operationEvidenceCreator);
    }

    @GetMapping("/{uuid}/delivery-addresses")
    public List<DeliveryAddressDTO> getUserAddresses(@PathVariable UUID uuid) {
        UserDTO userDTO = userService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return userDTO.getDeliveryAddress();
    }

}
