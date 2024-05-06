package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;

import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class DishDTO {


    @NotNull
    private UUID uuid;


    @NotNull
    @Min(1)
    private Integer quantity;


    @NotNull
    private ProductDTO productDTO;


    @Nullable
    private List<MenuItemDTO> menuItemDTOS;


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

    public ProductDTO getProduct() {
        return productDTO;
    }

    public void setProduct(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    @Nullable
    public List<MenuItemDTO> getMenuItems() {
        return menuItemDTOS;
    }

    public void setMenuItems(@Nullable List<MenuItemDTO> menuItemDTOS) {
        this.menuItemDTOS = menuItemDTOS;
    }

}
