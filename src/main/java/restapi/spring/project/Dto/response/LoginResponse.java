package restapi.spring.project.Dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String token;
    @Value("${security.jwt.expiration}")
    private Date expiresIn;
}
