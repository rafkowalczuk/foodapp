package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DelivererDTO;
import pl.polsl.foodapp.dto.OrderDTO;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.DelivererBuilder;
import pl.polsl.foodapp.entity.Order;
import pl.polsl.foodapp.repository.DelivererRepository;
import pl.polsl.foodapp.repository.OrderRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.polsl.foodapp.utils.ConverterUtils.convert;

@Service
public class DelivererServiceImpl {
    private final DelivererRepository delivererRepository;
    private final OrderRepository orderRepository;

    public DelivererServiceImpl(DelivererRepository delivererRepository, OrderRepository orderRepository) {
        this.delivererRepository = delivererRepository;
        this.orderRepository = orderRepository;
    }


    public List<DelivererDTO> getAll() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return delivererRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }


    public void put(UUID uuid, DelivererDTO delivererDTO) {
        if (!Objects.equal(delivererDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Deliverer deliverer = delivererRepository.findByUuid(delivererDTO.getUuid())
                .orElseGet(() -> newDeliverer(delivererDTO.getUuid()));

        List<Order> orders = new ArrayList<>();
        if (delivererDTO.getOrders() != null) {
            for (OrderDTO o : delivererDTO.getOrders()) {
                Order order = orderRepository.findByUuid(o.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                orders.add(order);
            }
        }

        deliverer.setPersonalData(convert(delivererDTO.getPersonalData()));
        deliverer.setLoginData(convert(delivererDTO.getLogginData()));
        deliverer.setArchive(delivererDTO.getArchive());
        deliverer.setOrders(orders);

        if (deliverer.getId() == null) {
            delivererRepository.save(deliverer);
        }
    }


    public void delete(UUID uuid) {
        Deliverer deliverer = delivererRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        delivererRepository.delete(deliverer);
    }


    public Optional<DelivererDTO> getByUuid(UUID uuid) {
        return delivererRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }


    public static Deliverer newDeliverer(UUID uuid) {
        return new DelivererBuilder()
                .withUuid(uuid)
                .build();
    }

}
