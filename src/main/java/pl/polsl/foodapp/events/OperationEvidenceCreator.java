package pl.polsl.foodapp.events;

import org.springframework.context.ApplicationEvent;
import pl.polsl.foodapp.dto.UserDTO;

public class OperationEvidenceCreator extends ApplicationEvent {

    private final UserDTO userDTO;

    public OperationEvidenceCreator(Object source, UserDTO userDTO) {
        super(source);
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
