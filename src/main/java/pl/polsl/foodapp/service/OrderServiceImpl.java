package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import jakarta.activation.UnsupportedDataTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.*;
import pl.polsl.foodapp.entity.*;
import pl.polsl.foodapp.entity.enums.EvidenceType;
import pl.polsl.foodapp.entity.enums.PriceType;
import pl.polsl.foodapp.repository.*;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DelivererRepository delivererRepository;
    private final MenuItemRepository menuItemRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemServiceImpl orderItemServiceImpl;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, DelivererRepository delivererRepository, MenuItemRepository menuItemRepository, DiscountCodeRepository discountCodeRepository, OrderItemRepository orderItemRepository, OrderItemServiceImpl orderItemServiceImpl) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.delivererRepository = delivererRepository;
        this.menuItemRepository = menuItemRepository;
        this.discountCodeRepository = discountCodeRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemServiceImpl = orderItemServiceImpl;
    }


    public List<OrderDTO> getAll() {
        return orderRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }


    public void put(UUID uuid, OrderDTO orderDTO) {
        if (!Objects.equal(orderDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUuid(orderDTO.getUser().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Restaurant restaurant = restaurantRepository.findByUuid(orderDTO.getRestaurant().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Deliverer deliverer = delivererRepository.findByUuid(orderDTO.getDeliverer().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Order order = orderRepository.findByUuid(orderDTO.getUuid())
                .orElseGet(() -> newOrder(uuid, user, restaurant));

        if (!Objects.equal(order.getUser().getUuid(), orderDTO.getUser().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equal(order.getRestaurant().getUuid(), orderDTO.getRestaurant().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (order.getOrderStatus().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItems = putOrderItems(orderDTO);
        DiscountCode discountCode = putDiscountCode(orderDTO);

        BigDecimal orderNettoPrice;
        BigDecimal orderBruttoPrice;
        BigDecimal amountToPayBrutto;
        try {
            orderNettoPrice = orderItemServiceImpl.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.NETTO);
            orderBruttoPrice = orderItemServiceImpl.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.BRUTTO);
            amountToPayBrutto = orderItemServiceImpl.applyDiscount(discountCode, orderBruttoPrice);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.setNote(orderDTO.getNote());
        order.setNettoPrice(orderNettoPrice);
        order.setBruttoPrice(orderBruttoPrice);
        order.setAmountToPayBrutto(amountToPayBrutto);
        order.setDiscountCode(discountCode);
        order.setOrderItems(orderItems);
        order.setDeliverer(deliverer);

        if (order.getId() == null) {
            orderRepository.save(order);
        }
    }


    public void delete(UUID uuid) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderRepository.delete(order);
    }


    public Optional<OrderDTO> getByUuid(UUID uuid) {
        return orderRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }


    public void setIsPaid(OrderDTO orderDTO) {
        Order order = orderRepository.findByUuid(orderDTO.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getPaid()) {
            order.getOrderStatus().setPaid(true);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    public void setIsGivedOut(UUID uuid, OrderStatusDTO orderStatusDTO) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getPaid() || order.getOrderStatus().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setGiveOutTime(orderStatusDTO.getGiveOutTime());
    }


    public void setIsDelivered(UUID uuid, OrderStatusDTO orderStatusDTO) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getPaid() || order.getOrderStatus().getGiveOutTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setDeliveryTime(orderStatusDTO.getDeliveryTime());
    }


    public UserDTO newOperationForPaidOrder(OrderDTO orderDTO) {
        User user = userRepository.findByUuid(orderDTO.getUser().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserDTO userDTO = ConverterUtils.convert(user);
        userDTO.setOperationEvidence(List.of(newEvidenceForOrderPayment(orderDTO)));
        return userDTO;
    }

    private OperationEvidenceDTO newEvidenceForOrderPayment(OrderDTO orderDTO) {
        return new OperationEvidenceDTOBuilder()
                .withDate(Instant.now())
                .withUser(orderDTO.getUser())
                .withAmount(orderDTO.getAmountToPayBrutto())
                .withType(EvidenceType.PAYMENT)
                .build();
    }

    private DiscountCode putDiscountCode(OrderDTO orderDTO) {
        DiscountCode discountCode = null;
        if (orderDTO.getDiscountCode() != null) {
            discountCode = discountCodeRepository.findByUuid(orderDTO.getDiscountCode().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            if (discountCode.getRestaurants() != null) {
                discountCode.getRestaurants().stream()
                        .filter(r -> r.getUuid().equals(orderDTO.getRestaurant().getUuid()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }
            if (discountCode.getUsers() != null) {
                discountCode.getUsers().stream()
                        .filter(u -> u.getUuid().equals(orderDTO.getUser().getUuid()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }
        }
        return discountCode;
    }

    private List<OrderItem> putOrderItems(OrderDTO orderDTO) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            MenuItem menuItem = menuItemRepository.findByUuid(orderItemDTO.getMenuItem().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            OrderItem orderItem = orderItemRepository.findByUuid(orderDTO.getUuid())
                    .orElseGet(() -> newOrderItem(orderDTO.getUuid()));
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setMenuItem(menuItem);
            if (orderItem.getId() == null) {
                orderItemRepository.save(orderItem);
            }
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private OrderItem newOrderItem(UUID uuid) {
        return new OrderItemBuilder()
                .withUuid(uuid)
                .build();
    }

    private Order newOrder(UUID uuid, User user, Restaurant restaurant) {
        return new OrderBuilder()
                .withUuid(uuid)
                .withUser(user)
                .withRestaurant(restaurant)
                .withOrderStatus(newOrderStatus())
                .build();
    }

    private OrderStatus newOrderStatus() {
        return new OrderStatusBuilder()
                .withOrderTime(Instant.now())
                .withPaid(false)
                .build();
    }

}
