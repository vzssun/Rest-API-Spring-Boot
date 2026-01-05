package restapi.spring.project.Dto.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserRequest {

    // @Size(min =3 max=100); Discuss whether it is interesting to paste into pom.xml import javax.validation.constraints.Size
    private String username;
    // @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")

    private String cpf;
    //@Email

    private String email;
    // @Size(min=6)
    private String password;


}