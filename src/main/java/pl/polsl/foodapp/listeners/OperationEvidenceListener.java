package pl.polsl.foodapp.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.foodapp.dto.OperationEvidenceDTO;
import pl.polsl.foodapp.dto.UserDTO;
import pl.polsl.foodapp.entity.OperationEvidence;
import pl.polsl.foodapp.entity.User;
import pl.polsl.foodapp.events.OperationEvidenceCreator;
import pl.polsl.foodapp.repository.OperationEvidenceRepository;
import pl.polsl.foodapp.repository.UserRepository;
import pl.polsl.foodapp.service.OperationEvidenceServiceImpl;
import pl.polsl.foodapp.utils.ConverterUtils;

import java.math.BigDecimal;

@Component
public class OperationEvidenceListener {

    private final OperationEvidenceServiceImpl operationEvidenceService;
    private final UserRepository userRepository;


    public OperationEvidenceListener(OperationEvidenceServiceImpl operationEvidenceService, UserRepository userRepository) {
        this.operationEvidenceService = operationEvidenceService;
        this.userRepository = userRepository;
    }

    @EventListener
    public void onAddOperation(OperationEvidenceCreator operationEvidenceCreator) {
        UserDTO userDTO = operationEvidenceCreator.getUserDTO();
        OperationEvidence operationEvidence = ConverterUtils.convert(userDTO.getOperationEvidence().stream().findFirst().orElseThrow());
        User user = userRepository.findByUuid(userDTO.getUuid()).orElseThrow();
        operationEvidence.setUser(user);

        validateAccountBalanceAfterOperation(operationEvidence);
        operationEvidenceService.add(operationEvidence);
    }

    private void validateAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        BigDecimal acconutBalanceAfterOperation = operationEvidenceService.getAccountBalanceAfterOperation(operationEvidence);
        if (acconutBalanceAfterOperation.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
