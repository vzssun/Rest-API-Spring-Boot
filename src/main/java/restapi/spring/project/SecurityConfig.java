package restapi.spring.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Define in-memory authentication with username and password
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password("{noop}password")  // {noop} indicates no password encoder (plain text)
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST API
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/api/**").permitAll() // Allow unauthenticated access to root, home, and API endpoints
                .anyRequest().authenticated()  // Secure other URLs
            )
            .httpBasic(httpBasic -> {})  // Use HTTP Basic authentication
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())  // Disable the X-Frame-Options header
            );
        return http.build();
    }
}