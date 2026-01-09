package restapi.spring.project.Services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restapi.spring.project.Dto.request.LoginRequest;
import restapi.spring.project.Dto.request.RegisterRequest;
import restapi.spring.project.Dto.response.LoginResponse;
import restapi.spring.project.Model.UserModel;
import restapi.spring.project.Repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public RegisterRequest createUser(RegisterRequest request) {
        UserModel user = new UserModel();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return request;

    }

    public LoginResponse authenticate(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserModel user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        return new LoginResponse(user.getUserId(), user.getEmail(), user.getExpiredAt());
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserModel safeUser = new UserModel();
                    safeUser.setUserId(user.getUserId());
                    safeUser.setFullName(user.getFullName());
                    safeUser.setEmail(user.getEmail());
                    safeUser.setCreatedAt(user.getCreatedAt());
                    safeUser.setExpiredAt(user.getExpiredAt());
                    return safeUser;
                })
                .collect(Collectors.toList());
//Annotation Feature: In the future, place objects in entities of how many books Usario has reserved or rented

    }

    public List<UserModel> getExpiredUsers() {
        Date now = new Date();
        return userRepository.findByExpiredAtBefore(now);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}


