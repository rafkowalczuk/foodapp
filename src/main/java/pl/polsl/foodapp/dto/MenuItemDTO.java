package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;
import pl.polsl.foodapp.entity.enums.VaxTax;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class MenuItemDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;


    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal nettoPrice;


    @NotNull
    private VaxTax vatTax;


    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal bruttoPrice;


    @NotNull
    @Size(min = 1)
    private List<DishDTO> dishDTOS;


    @NotNull
    private RestaurantDTO restaurantDTO;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(BigDecimal nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public VaxTax getVatTax() {
        return vatTax;
    }

    public void setVatTax(VaxTax vatTax) {
        this.vatTax = vatTax;
    }

    public BigDecimal getBruttoPrice() {
        return bruttoPrice;
    }

    public void setBruttoPrice(BigDecimal bruttoPrice) {
        this.bruttoPrice = bruttoPrice;
    }

    public List<DishDTO> getDishes() {
        return dishDTOS;
    }

    public void setDishes(List<DishDTO> dishDTOS) {
        this.dishDTOS = dishDTOS;
    }

    public RestaurantDTO getRestaurant() {
        return restaurantDTO;
    }

    public void setRestaurant(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

}
