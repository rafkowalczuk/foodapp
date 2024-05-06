package pl.polsl.foodapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.OpenTimeDTO;
import pl.polsl.foodapp.service.OpenTimeServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/open-times")
public class OpenTimeController {

  private final OpenTimeServiceImpl openTimeService;

    public OpenTimeController(OpenTimeServiceImpl openTimeService) {
        this.openTimeService = openTimeService;
    }


  @GetMapping
  public List<OpenTimeDTO> get() {
    return openTimeService.getAll();
  }

  @Transactional
  @PostMapping
  public void post(@RequestBody List<@Valid OpenTimeDTO> openTimesJson) {
    for (OpenTimeDTO openTimeDTO : openTimesJson) {
      put(openTimeDTO.getUuid(), openTimeDTO);
    }
  }

  @GetMapping("/{uuid}")
  public OpenTimeDTO get(@PathVariable UUID uuid) {
    return openTimeService.getByUuid(uuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Transactional
  @PutMapping("/{uuid}")
  public void put(@PathVariable UUID uuid, @RequestBody @Valid OpenTimeDTO json) {
    openTimeService.put(uuid, json);
  }

  @Transactional
  @DeleteMapping("/{uuid}")
  public void delete(@PathVariable UUID uuid) {
    openTimeService.delete(uuid);
  }

}
