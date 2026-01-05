package restapi.spring.project.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import restapi.spring.project.Model.UserModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String email;
    private String cpf;
    private String username;

    public UserResponse(UserModel usermodel) {
        this.userId = usermodel.getUserId();
        this.username = usermodel.getUsername();
        this.email = usermodel.getEmail();
        this.cpf = usermodel.getCpf();
    }
}