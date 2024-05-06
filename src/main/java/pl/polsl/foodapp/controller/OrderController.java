package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DelivererDTO;
import pl.polsl.foodapp.dto.OrderDTO;
import pl.polsl.foodapp.dto.OrderStatusDTO;
import pl.polsl.foodapp.dto.UserDTO;
import pl.polsl.foodapp.events.OperationEvidenceCreator;
import pl.polsl.foodapp.service.DelivererServiceImpl;
import pl.polsl.foodapp.service.OrderServiceImpl;
import pl.polsl.foodapp.service.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final DelivererServiceImpl delivererService;
    private final UserServiceImpl userService;
    private final ApplicationEventPublisher eventPublisher;

    public OrderController(OrderServiceImpl orderService, DelivererServiceImpl delivererService, UserServiceImpl userService, ApplicationEventPublisher eventPublisher) {
        this.orderService = orderService;
        this.delivererService = delivererService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }



    @GetMapping
    public List<OrderDTO> get() {
        return orderService.getAll();
    }


    @GetMapping(params = {"user"})
    public List<OrderDTO> getByUser(@RequestParam("user") UUID userUuid) {
        UserDTO userDTO = userService.getByUuid(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return userDTO.getOrders();
    }


    @GetMapping(params = {"deliverer"})
    public List<OrderDTO> getByDeliverer(@RequestParam("delivererUuid") UUID delivererUuid) {
        DelivererDTO delivererDTO = delivererService.getByUuid(delivererUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return delivererDTO.getOrders();
    }


    @GetMapping("/{uuid}")
    public OrderDTO get(@PathVariable UUID uuid) {
        return orderService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid OrderDTO json) {
        orderService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        orderService.delete(uuid);
    }

    @Transactional
    @PatchMapping("/{uuid}/paid")
    public void patchIsPaid(@PathVariable UUID uuid) {
        OrderDTO orderDTO = orderService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderService.setIsPaid(orderDTO);

        OperationEvidenceCreator operationEvidenceCreator = new OperationEvidenceCreator(this, orderService.newOperationForPaidOrder(orderDTO));
        eventPublisher.publishEvent(operationEvidenceCreator);
    }

    @Transactional
    @PatchMapping("/{uuid}/gived-out")
    public void patchIsGivedOut(@PathVariable UUID uuid, @RequestBody @Valid OrderStatusDTO orderStatusJson) {
        orderService.setIsGivedOut(uuid, orderStatusJson);
    }

    @Transactional
    @PatchMapping("/{uuid}/delivered")
    public void patchIsDelivered(@PathVariable UUID uuid, @RequestBody @Valid OrderStatusDTO orderStatusJson) {
        orderService.setIsDelivered(uuid, orderStatusJson);
    }

}
