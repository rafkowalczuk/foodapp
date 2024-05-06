package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;
import pl.polsl.foodapp.entity.enums.Archive;

import java.util.UUID;
@GeneratePojoBuilder
public class EmployeeDTO {


    @NotNull
    private UUID uuid;


    @NotNull
    @Embedded
    private PersonalDataDTO personalDataDTO;


    @NotNull
    @Embedded
    private LogginDataDTO logginData;


    @NotNull
    private Archive archive;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PersonalDataDTO getPersonalData() {
        return personalDataDTO;
    }

    public void setPersonalData(PersonalDataDTO personalDataDTO) {
        this.personalDataDTO = personalDataDTO;
    }

    public LogginDataDTO getLogginData() {
        return logginData;
    }

    public void setLogginData(LogginDataDTO logginData) {
        this.logginData = logginData;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

}
