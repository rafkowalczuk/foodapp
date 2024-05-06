package pl.polsl.foodapp.service;

import jakarta.activation.UnsupportedDataTypeException;
import org.springframework.stereotype.Service;
import pl.polsl.foodapp.entity.DiscountCode;
import pl.polsl.foodapp.entity.OrderItem;
import pl.polsl.foodapp.entity.enums.PriceType;
import pl.polsl.foodapp.repository.OrderItemRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderItemServiceImpl {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }



    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }


    public void add(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }


    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }


    public Optional<OrderItem> getByUuid(UUID uuid) {
        return orderItemRepository.findByUuid(uuid);
    }


    public BigDecimal calculatePrice(List<OrderItem> orderItemList, BigDecimal startPrice, PriceType priceType) throws UnsupportedDataTypeException {
        BigDecimal orderPrice = startPrice;

        for (OrderItem orderItem : orderItemList) {
            orderPrice = switch (priceType) {
                case NETTO -> orderPrice.add(
                        orderItem.getMenuItem().getNettoPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                );
                case BRUTTO -> orderPrice.add(
                        orderItem.getMenuItem().getBruttoPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                );
                default -> throw new UnsupportedDataTypeException();
            };
        }
        return orderPrice;
    }


    public BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderBruttoPrice) throws UnsupportedDataTypeException {
        if (discountCode == null) {
            return orderBruttoPrice;
        }

        BigDecimal amountToPayBrutto = switch (discountCode.getDiscountUnit()) {
            case PLN -> orderBruttoPrice.subtract(discountCode.getDiscount());
            case PERCENT -> orderBruttoPrice.multiply(
                    BigDecimal.valueOf(100).subtract(discountCode.getDiscount())
            ).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            default -> throw new UnsupportedDataTypeException();
        };

        return amountToPayBrutto;
    }

}
