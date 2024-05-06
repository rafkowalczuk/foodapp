package pl.polsl.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.polsl.foodapp.entity.Deliverer;
import pl.polsl.foodapp.entity.OperationEvidence;
import pl.polsl.foodapp.entity.User;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface OperationEvidenceRepository extends JpaRepository<OperationEvidence, Long> {

    @Query("SELECT COALESCE(SUM(" +
            "CASE " +
            "WHEN e.type = pl.polsl.foodapp.entity.enums.EvidenceType.DEPOSIT THEN e.amount " +
            "WHEN e.type = pl.polsl.foodapp.entity.enums.EvidenceType.WITHDRAW " +
            "or e.type = pl.polsl.foodapp.entity.enums.EvidenceType.PAYMENT THEN -e.amount " +
            "ELSE 0 " +
            "END), 0) FROM OperationEvidence e WHERE e.user = :user")
    BigDecimal getUserAccountBalance(@Param("user") User user);
}
