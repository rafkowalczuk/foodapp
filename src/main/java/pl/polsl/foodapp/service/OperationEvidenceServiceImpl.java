package pl.polsl.foodapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.EmployeeDTO;
import pl.polsl.foodapp.entity.MenuItem;
import pl.polsl.foodapp.entity.OperationEvidence;
import pl.polsl.foodapp.entity.User;
import pl.polsl.foodapp.repository.OperationEvidenceRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class OperationEvidenceServiceImpl {

    private final OperationEvidenceRepository operationEvidenceRepository;

    public OperationEvidenceServiceImpl(OperationEvidenceRepository operationEvidenceRepository) {
        this.operationEvidenceRepository = operationEvidenceRepository;
    }


    public List<OperationEvidence> getAll() {
        return operationEvidenceRepository.findAll();
    }


    public void add(OperationEvidence operationEvidence) {
        operationEvidenceRepository.save(operationEvidence);
    }


    public void delete(OperationEvidence operationEvidence) {
        operationEvidenceRepository.delete(operationEvidence);
    }


    public BigDecimal getUserAccountBalance(User user) {
        return operationEvidenceRepository.getUserAccountBalance(user);
    }


    public BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        BigDecimal balanceBefore = getUserAccountBalance(operationEvidence.getUser());
        BigDecimal balanceAfter = switch (operationEvidence.getType()) {
            case WITHDRAW, PAYMENT -> balanceBefore.subtract(operationEvidence.getAmount());
            case DEPOSIT -> balanceBefore.add(operationEvidence.getAmount());
            default -> throw new UnsupportedOperationException();
        };

        return balanceAfter;
    }
}
