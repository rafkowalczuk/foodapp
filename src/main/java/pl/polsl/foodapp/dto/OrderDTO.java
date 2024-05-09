package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class OrderDTO {


   /* public interface OrderValidation {}
    public interface OrderStatusValidation {}*/


    @JsonView(JsonViews.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(JsonViews.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal nettoPrice;

    @JsonView(JsonViews.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal bruttoPrice;

    @JsonView(JsonViews.Extended.class)
    @Nullable
    private DiscountCodeDTO discountCodeDTO;

    @JsonView(JsonViews.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal amountToPayBrutto;

    @JsonView(JsonViews.Extended.class)
    @Nullable
    private String note;


    @JsonView(JsonViews.Basic.class)
    @NotNull
    @Embedded
    private OrderStatusDTO orderStatusDTO;

    @JsonView(JsonViews.Extended.class)
    @NotNull
    private DeliveryAddressDTO deliveryAddressDTO;

    @JsonView(JsonViews.Extended.class)
    @NotNull
    @Size(min = 1)
    private List<OrderItemDTO> orderItemDTOS;

    @JsonView(JsonViews.Basic.class)
    @NotNull
    private UserDTO user;

    @JsonView(JsonViews.Basic.class)
    @NotNull
    private DelivererDTO delivererDTO;

    @JsonView(JsonViews.Basic.class)
    @NotNull
    private RestaurantDTO restaurantDTO;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(BigDecimal nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public BigDecimal getBruttoPrice() {
        return bruttoPrice;
    }

    public void setBruttoPrice(BigDecimal bruttoPrice) {
        this.bruttoPrice = bruttoPrice;
    }

    @Nullable
    public DiscountCodeDTO getDiscountCode() {
        return discountCodeDTO;
    }

    public void setDiscountCode(@Nullable DiscountCodeDTO discountCodeDTO) {
        this.discountCodeDTO = discountCodeDTO;
    }

    public BigDecimal getAmountToPayBrutto() {
        return amountToPayBrutto;
    }

    public void setAmountToPayBrutto(BigDecimal amountToPayBrutto) {
        this.amountToPayBrutto = amountToPayBrutto;
    }

    @Nullable
    public String getNote() {
        return note;
    }

    public void setNote(@Nullable String note) {
        this.note = note;
    }

    public OrderStatusDTO getOrderStatusDTO() {
        return orderStatusDTO;
    }

    public void setOrderStatus(OrderStatusDTO orderStatusDTO) {
        this.orderStatusDTO = orderStatusDTO;
    }

    public DeliveryAddressDTO getDeliveryAddressDTO() {
        return deliveryAddressDTO;
    }

    public void setDeliveryAddressDTO(DeliveryAddressDTO deliveryAddressDTO) {
        this.deliveryAddressDTO = deliveryAddressDTO;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItemDTOS;
    }

    public void setOrderItems(List<OrderItemDTO> orderItemDTOS) {
        this.orderItemDTOS = orderItemDTOS;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public DelivererDTO getDeliverer() {
        return delivererDTO;
    }

    public void setDeliverer(DelivererDTO delivererDTO) {
        this.delivererDTO = delivererDTO;
    }

    public RestaurantDTO getRestaurant() {
        return restaurantDTO;
    }

    public void setRestaurant(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

}
