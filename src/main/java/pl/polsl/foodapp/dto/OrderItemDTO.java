package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;

import java.util.UUID;
@GeneratePojoBuilder
public class OrderItemDTO {

    @NotNull
    private UUID uuid;


    @NotNull
    @Min(1)
    private Integer quantity;


    @NotNull
    private MenuItemDTO menuItemDTO;



    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MenuItemDTO getMenuItem() {
        return menuItemDTO;
    }

    public void setMenuItem(MenuItemDTO menuItemDTO) {
        this.menuItemDTO = menuItemDTO;
    }

}
