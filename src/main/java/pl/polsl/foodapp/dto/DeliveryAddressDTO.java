package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;

import java.util.UUID;
@GeneratePojoBuilder
public class DeliveryAddressDTO {

    @NotNull
    private UUID uuid;

    @Nullable
    private String description;


    @NotNull
    private String street;


    @NotNull
    private String streetNumber;


    @NotNull
    private String localNumber;


    @NotNull
    private String postcode;


    @NotNull
    private String city;


    @Nullable
    private String borough;


    @Nullable
    private String county;


    @Nullable
    private String state;


    @NotNull
    private UserDTO user;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Nullable
    public String getBorough() {
        return borough;
    }

    public void setBorough(@Nullable String borough) {
        this.borough = borough;
    }

    @Nullable
    public String getCounty() {
        return county;
    }

    public void setCounty(@Nullable String county) {
        this.county = county;
    }

    @Nullable
    public String getState() {
        return state;
    }

    public void setState(@Nullable String state) {
        this.state = state;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
