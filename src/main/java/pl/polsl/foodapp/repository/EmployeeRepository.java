package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.Employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUuid(UUID uuid);

}
