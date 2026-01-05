package restapi.spring.project.Dto.response;

import lombok.Getter;
import restapi.spring.project.Model.UserModel;

@Getter
public class UserResponse {
    private final Long id;
    private final String email;
    private final String cpf;
    private final String username;

    public UserResponse(UserModel usermodel) {
        this.id = usermodel.getId();
        this.username = usermodel.getUsername();
        this.email = usermodel.getEmail();
        this.cpf = usermodel.getCpf();
    }
}