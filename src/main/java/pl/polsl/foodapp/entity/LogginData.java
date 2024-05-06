package pl.polsl.foodapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@Embeddable
public class LogginData {
    @Column(unique = true)
    @Min(3)
    private String login;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

