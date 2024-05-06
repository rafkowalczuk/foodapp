package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;
import pl.polsl.foodapp.entity.enums.Archive;

import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class UserDTO {


    @NotNull
    private UUID uuid;


    @NotNull
    @Embedded
    private PersonalDataDTO personalData;


    @Nullable
    private List<DeliveryAddressDTO> deliveryAddress;


    @NotNull
    @Embedded
    private LogginDataDTO logginData;

    @JsonIgnore
    @Nullable
    private List<OrderDTO> orders;


    @NotNull
    private List<OperationEvidenceDTO> operationEvidence;


    @Nullable
    private List<DiscountCodeDTO> discountCodes;


    @NotNull
    private Archive archive;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PersonalDataDTO getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalDataDTO personalData) {
        this.personalData = personalData;
    }

    @Nullable
    public List<DeliveryAddressDTO> getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(@Nullable List<DeliveryAddressDTO> deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LogginDataDTO getLogginData() {
        return logginData;
    }

    public void setLogginData(LogginDataDTO logginData) {
        this.logginData = logginData;
    }

    @Nullable
    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(@Nullable List<OrderDTO> orders) {
        this.orders = orders;
    }

    public List<OperationEvidenceDTO> getOperationEvidence() {
        return operationEvidence;
    }

    public void setOperationEvidence(List<OperationEvidenceDTO> operationEvidence) {
        this.operationEvidence = operationEvidence;
    }

    @Nullable
    public List<DiscountCodeDTO> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(@Nullable List<DiscountCodeDTO> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
