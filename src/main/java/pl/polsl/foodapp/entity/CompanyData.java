package pl.polsl.foodapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
//import jakarta.persistence.Embedded;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@Embeddable
public class CompanyData {
    @NotNull
    @Column(name="companyName")
    private String name;

    @Embedded
    @NotNull
    private Address address;

    @NotNull
    private String nip;

    @NotNull
    private String regon;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAdress() {
        return address;
    }

    public void setAdress(Address adress) {
        this.address = adress;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}