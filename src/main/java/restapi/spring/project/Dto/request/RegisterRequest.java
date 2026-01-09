package restapi.spring.project.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    // @Email
    private String email;
    // @Size(min=3 max=100)
    private String fullName;
    // @Size(min=6)
    private String password;


}