package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.UserDTO;
import pl.polsl.foodapp.entity.User;
import pl.polsl.foodapp.entity.UserBuilder;
import pl.polsl.foodapp.repository.UserRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.polsl.foodapp.utils.ConverterUtils.convert;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }


    public void put(UUID uuid, UserDTO userDTO) {
        if (!Objects.equal(userDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUuid(userDTO.getUuid())
                .orElseGet(() -> newUser(uuid));

        user.setPersonalData(convert(userDTO.getPersonalData()));
        user.setLogginData(convert(userDTO.getLogginData()));
        user.setArchive(userDTO.getArchive());

        if (user.getId() == null) {
            userRepository.save(user);
        }
    }


    public void delete(UUID uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }


    public Optional<UserDTO> getByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }


    public void validateNewOperation(UUID uuid, UserDTO userDTO) {
        if(!Objects.equal(userDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userRepository.findByUuid(userDTO.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private User newUser(UUID uuid) {
        return new UserBuilder()
                .withUuid(uuid)
                .build();
    }
}
