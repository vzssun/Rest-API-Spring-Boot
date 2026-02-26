package restapi.spring.project.Dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.spring.project.Enum.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email(message = "Email deve ter formato válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;
    @Size(min=6)
    @NotBlank(message = "Senha é obrigatória")
    private String password;

    private Role role;

}