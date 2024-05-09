package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Joiner;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Embeddable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;
import pl.polsl.foodapp.entity.enums.Sex;
@GeneratePojoBuilder
@Embeddable
public class PersonalDataDTO {


    @JsonView(JsonViews.Basic.class)
    @Nullable
    private String name;

    @JsonView(JsonViews.Basic.class)
    @Nullable
    private String surname;

    @JsonView(JsonViews.Extended.class)
    @Nullable
    private Sex sex;

    @JsonView(JsonViews.Extended.class)
    @Nullable
    private String phone;

    @JsonView(JsonViews.Extended.class)
    @Nullable
    private String email;

    @JsonView(JsonViews.Basic.class)
    public String nameAndSurname() {
        return Joiner.on(" ").skipNulls().join(name, surname);
    }


    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getSurname() {
        return surname;
    }

    public void setSurname(@Nullable String surname) {
        this.surname = surname;
    }

    @Nullable
    public Sex getSex() {
        return sex;
    }

    public void setSex(@Nullable Sex sex) {
        this.sex = sex;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }
}
