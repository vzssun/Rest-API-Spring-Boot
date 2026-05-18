package restapi.spring.project.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import restapi.spring.project.Dto.request.LoginRequest;
import restapi.spring.project.Dto.request.LogoutRequest;
import restapi.spring.project.Dto.request.RegisterRequest;
import restapi.spring.project.Dto.response.LoginResponse;
import restapi.spring.project.Services.AuthenticationService;
import restapi.spring.project.Services.JwtService;

import java.util.Optional;
@Tag(name = "Authentication", description = "Registro, login e logout com blacklist de token JWT")
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    // private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        // this.jwtService = jwtService;
        this.authenticationService = authenticationService;

    }
    @Operation(summary = "Registra um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos para registro")
    @PostMapping("/signup")
    public ResponseEntity<RegisterRequest> register(@RequestBody RegisterRequest request) {
        RegisterRequest register = authenticationService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }
    @Operation(summary = "Realiza login e retorna JWT")
    @ApiResponse(responseCode = "202", description = "Login realizado, token retornado")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @PostMapping("/login")
    public ResponseEntity<Optional<LoginResponse>> authenticate(@RequestBody LoginRequest request) {
        Optional<LoginResponse> response = Optional.ofNullable(authenticationService.authenticate(request));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

    }
    @Operation(summary = "Realiza logout e revoga o token", description = "Adiciona o token na blacklist do cache. Token se torna inválido imediatamente mesmo antes de expirar")
    @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
    @DeleteMapping("/logout")
    public ResponseEntity<LogoutRequest> logout(@RequestBody LogoutRequest request) {
        LogoutRequest logout = authenticationService.logout(request);
        return ResponseEntity.status(HttpStatus.OK).body(logout);
    }

}