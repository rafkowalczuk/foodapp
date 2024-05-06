package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DeliveryAddressDTO;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.DeliveryAddress;
import pl.polsl.foodapp.entity.DeliveryAddressBuilder;
import pl.polsl.foodapp.entity.User;
import pl.polsl.foodapp.repository.DeliveryAddressRepository;
import pl.polsl.foodapp.repository.UserRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryAddressServiceImpl {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final UserRepository userRepository;

    public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository, UserRepository userRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.userRepository = userRepository;
    }

   public List<DeliveryAddressDTO> getAll() {
        return deliveryAddressRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    public void put(UUID uuid, DeliveryAddressDTO deliveryAddressDTO) {
        if (!Objects.equal(deliveryAddressDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUuid(deliveryAddressDTO.getUser().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByUuid(deliveryAddressDTO.getUuid())
                .orElseGet(() -> newDeliveryAddress(uuid, user));

        if (!Objects.equal(deliveryAddress.getUser().getUuid(), deliveryAddressDTO.getUser().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        deliveryAddress.setDescription(deliveryAddressDTO.getDescription());
        deliveryAddress.setStreet(deliveryAddressDTO.getStreet());
        deliveryAddress.setStreetNumber(deliveryAddressDTO.getStreetNumber());
        deliveryAddress.setLocalNumber(deliveryAddressDTO.getLocalNumber());
        deliveryAddress.setPostcode(deliveryAddressDTO.getPostcode());
        deliveryAddress.setCity(deliveryAddressDTO.getCity());
        deliveryAddress.setBorough(deliveryAddressDTO.getBorough());
        deliveryAddress.setCounty(deliveryAddressDTO.getCounty());
        deliveryAddress.setState(deliveryAddressDTO.getState());

        if (deliveryAddress.getId() == null) {
            deliveryAddressRepository.save(deliveryAddress);
        }
    }

    public void delete(UUID uuid) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        deliveryAddressRepository.delete(deliveryAddress);
    }

    public Optional<DeliveryAddressDTO> getByUuid(UUID uuid) {
        return deliveryAddressRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private DeliveryAddress newDeliveryAddress(UUID uuid, User user) {
        return new DeliveryAddressBuilder()
                .withUuid(uuid)
                .withUser(user)
                .build();
    }

}
