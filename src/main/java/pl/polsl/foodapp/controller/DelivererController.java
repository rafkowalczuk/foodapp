package pl.polsl.foodapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DelivererDTO;
import pl.polsl.foodapp.service.DelivererServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/deliverers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DelivererController {



  private final DelivererServiceImpl delivererService;

    public DelivererController(DelivererServiceImpl delivererService) {
        this.delivererService = delivererService;
    }

    @GetMapping
    public List<DelivererDTO> get() {
        return delivererService.getAll();
    }
    @GetMapping("/{uuid}")
    public DelivererDTO get(@PathVariable UUID uuid) {
        return delivererService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody DelivererDTO delivererDTO) {
        delivererService.put(uuid, delivererDTO);
    }
    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        delivererService.delete(uuid);
    }


}
