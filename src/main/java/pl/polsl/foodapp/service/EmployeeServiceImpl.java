package pl.polsl.foodapp.service;

import com.google.common.base.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.DiscountCodeDTO;
import pl.polsl.foodapp.dto.EmployeeDTO;
import pl.polsl.foodapp.entity.Dish;
import pl.polsl.foodapp.entity.Employee;
import pl.polsl.foodapp.entity.EmployeeBuilder;
import pl.polsl.foodapp.repository.EmployeeRepository;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.polsl.foodapp.utils.ConverterUtils.convert;

@Service
public class EmployeeServiceImpl {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    public void put(UUID uuid, EmployeeDTO employeeDTO) {
        if (!Objects.equal(employeeDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Employee employee = employeeRepository.findByUuid(employeeDTO.getUuid())
                .orElseGet(() -> newEmployee(uuid));

        employee.setPersonalData(convert(employeeDTO.getPersonalData()));
        employee.setLoginData(convert(employeeDTO.getLogginData()));
        employee.setArchive(employeeDTO.getArchive());

        if (employee.getId() == null) {
            employeeRepository.save(employee);
        }
    }

    public void delete(UUID uuid) {
        Employee employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        employeeRepository.delete(employee);
    }

    public Optional<EmployeeDTO> getByUuid(UUID uuid) {
        return employeeRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Employee newEmployee(UUID uuid) {
        return new EmployeeBuilder()
                .withUuid(uuid)
                .build();
    }

}
