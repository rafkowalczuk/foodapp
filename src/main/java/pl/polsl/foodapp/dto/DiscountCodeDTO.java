package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;
import pl.polsl.foodapp.entity.enums.DiscountUnit;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class DiscountCodeDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String code;


    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal discount;


    @NotNull
    private DiscountUnit discountUnit;


    @NotNull
    @Embedded
    private PeriodDTO period;


    @Nullable
    private List<UserDTO> users;


    @Nullable
    private List<RestaurantDTO> restaurantDTOS;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public DiscountUnit getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(DiscountUnit discountUnit) {
        this.discountUnit = discountUnit;
    }

    public PeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(PeriodDTO period) {
        this.period = period;
    }

    @Nullable
    public List<RestaurantDTO> getRestaurantDTOS() {
        return restaurantDTOS;
    }

    public void setRestaurantDTOS(@Nullable List<RestaurantDTO> restaurantDTOS) {
        this.restaurantDTOS = restaurantDTOS;
    }

    @Nullable
    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(@Nullable List<UserDTO> users) {
        this.users = users;
    }

    @Nullable
    public List<RestaurantDTO> getRestaurants() {
        return restaurantDTOS;
    }

    public void setRestaurants(@Nullable List<RestaurantDTO> restaurantDTOS) {
        this.restaurantDTOS = restaurantDTOS;
    }

}
