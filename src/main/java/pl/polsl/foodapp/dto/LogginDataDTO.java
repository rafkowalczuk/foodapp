package pl.polsl.foodapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.polsl.foodapp.common.views.JsonViews;

@GeneratePojoBuilder
@Embeddable
public class LogginDataDTO {



    @JsonView(JsonViews.Basic.class)
    @Size(min = 3)
    private String login;

    @JsonIgnore
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
