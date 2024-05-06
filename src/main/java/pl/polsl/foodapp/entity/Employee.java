package pl.polsl.foodapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.entity.enums.Archive;


import java.util.UUID;
@GeneratePojoBuilder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("employee")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    private UUID uuid;

    @NotNull
    @Embedded
    private PersonalData personalData;

    @NotNull
    @Embedded
    private LogginData loginData;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LogginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LogginData loginData) {
        this.loginData = loginData;
    }


    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
