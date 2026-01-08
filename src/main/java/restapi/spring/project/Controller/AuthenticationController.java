package restapi.spring.project.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import restapi.spring.project.Dto.request.LoginRequest;
import restapi.spring.project.Dto.request.RegisterRequest;
import restapi.spring.project.Dto.response.LoginResponse;
import restapi.spring.project.Services.AuthenticationService;
import restapi.spring.project.Services.JwtService;

import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;

    }
    @PostMapping("/signup")
    public ResponseEntity<RegisterRequest> register(@RequestBody RegisterRequest request) {
        RegisterRequest register = authenticationService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }
    @PostMapping("/login")
    public ResponseEntity<Optional<LoginResponse>> authenticate(@RequestBody LoginRequest request) {
        Optional<LoginResponse> response = Optional.ofNullable(authenticationService.authenticate(request));
        response.ifPresent(r ->{
            UserDetails userDetails = authenticationService.loadUserByUsername(request.getEmail());
            String token = jwtService.generateToken(userDetails);
            r.setToken(token);
        });
        return ResponseEntity.ok(response);

    }
    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        // Invalidate the JWT token on the client side
        return ResponseEntity.ok("User logged out successfully.");
    }

}