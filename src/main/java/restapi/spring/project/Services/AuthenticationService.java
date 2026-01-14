package restapi.spring.project.Services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restapi.spring.project.Dto.request.LoginRequest;
import restapi.spring.project.Dto.request.LogoutRequest;
import restapi.spring.project.Dto.request.RegisterRequest;
import restapi.spring.project.Dto.response.LoginResponse;
import restapi.spring.project.Model.UserModel;
import restapi.spring.project.Repository.UserRepository;
import restapi.spring.project.Model.RolesModel;
import restapi.spring.project.Repository.RolesRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public RegisterRequest createUser(RegisterRequest request) {
        UserModel user = new UserModel();
        // Fetch the USER role from database
        RolesModel userRole = rolesRepository.findByRoleName("USER");

        // Create a Set and add the role to it
        Set<RolesModel> roles = new HashSet<>();
        roles.add(userRole);

        // Set the roles on the user
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles); // Default role USER

        userRepository.save(user);
        return request;

    }

    public LoginResponse authenticate(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserModel user = (UserModel) auth.getPrincipal();
        String token = jwtService.generateToken(new HashMap<>(), user);
        user.setToken(token);
        user.setExpiredAt(jwtService.getExpirationDateFromToken(token));
        userRepository.save(user);

        return new LoginResponse(user.getUserId(), user.getToken(), user.getExpiredAt());
    };

    public LogoutRequest logout(LogoutRequest request) {
        UserModel user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setToken(null);
        user.setExpiredAt(null);
        userRepository.save(user);
        return request;
    }

/*    public UserModel createAdministrator(RegisterRequest request) {
        UserModel admin = new UserModel();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(new Date());

        return userRepository.save(admin);
    }*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}


