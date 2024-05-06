package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;
import pl.polsl.foodapp.entity.enums.DayOfWeek;

import java.util.UUID;
@GeneratePojoBuilder
public class OpenTimeDTO {

    @NotNull
    private UUID uuid;


    @NotNull
    private DayOfWeek dayOfWeek;


    @NotNull
    @Embedded
    private PeriodTimeDTO periodTimeDTO;


    @NotNull
    private RestaurantDTO restaurantDTO;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public PeriodTimeDTO getPeriodTimeDTO() {
        return periodTimeDTO;
    }

    public void setPeriodTime(PeriodTimeDTO periodTimeDTO) {
        this.periodTimeDTO = periodTimeDTO;
    }

    public RestaurantDTO getRestaurantDTO() {
        return restaurantDTO;
    }

    public void setRestaurant(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

}
