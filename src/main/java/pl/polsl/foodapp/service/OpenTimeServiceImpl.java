package pl.polsl.foodapp.service;


import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.OpenTimeDTO;
import pl.polsl.foodapp.entity.MenuItem;
import pl.polsl.foodapp.entity.OpenTime;
import pl.polsl.foodapp.entity.OpenTimeBuilder;
import pl.polsl.foodapp.entity.Restaurant;
import pl.polsl.foodapp.repository.OpenTimeRepository;
import pl.polsl.foodapp.repository.RestaurantRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.polsl.foodapp.utils.ConverterUtils.convert;

@Service
public class OpenTimeServiceImpl {

    private final OpenTimeRepository openTimeRepository;
    private final RestaurantRepository restaurantRepository;

    public OpenTimeServiceImpl(OpenTimeRepository openTimeRepository, RestaurantRepository restaurantRepository) {
        this.openTimeRepository = openTimeRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<OpenTimeDTO> getAll() {
        return openTimeRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    public void put(UUID uuid, OpenTimeDTO openTimeDTO) {
        if (!Objects.equal(openTimeDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = restaurantRepository.findByUuid(openTimeDTO.getRestaurantDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        OpenTime openTime = openTimeRepository.findByUuid(openTimeDTO.getUuid())
                .orElseGet(() -> newOpenTime(uuid, restaurant));

        if (!Objects.equal(openTime.getRestaurant().getUuid(), openTimeDTO.getRestaurantDTO().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        openTime.setDayOfWeek(openTimeDTO.getDayOfWeek());
        openTime.setPeriodTime(convert(openTimeDTO.getPeriodTimeDTO()));

        if (openTime.getId() == null) {
            openTimeRepository.save(openTime);
        }
    }

    public void delete(UUID uuid) {
        OpenTime openTime = openTimeRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        openTimeRepository.delete(openTime);
    }

    public Optional<OpenTimeDTO> getByUuid(UUID uuid) {
        return openTimeRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private OpenTime newOpenTime(UUID uuid, Restaurant restaurant) {
        return new OpenTimeBuilder()
                .withUuid(uuid)
                .withRestaurant(restaurant)
                .build();
    }
}
