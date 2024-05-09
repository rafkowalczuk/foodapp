package pl.polsl.foodapp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.common.views.DelivererViews;
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

    @JsonView(DelivererViews.ListView.class)
    @GetMapping
    public List<DelivererDTO> get() {
        return delivererService.getAll();
    }
    @JsonView(DelivererViews.FullView.class)
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
