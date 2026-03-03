package restapi.spring.project.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static restapi.spring.project.Enum.Permission.*;
import static restapi.spring.project.Enum.Role.*;
// import restapi.spring.project.ApiKeyFilter.ApiKeyFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final RateLimitFilter rateLimitFilter;

        // private final ApiKeyFilter apiKeyFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider,
                        JwtAuthenticationFilter jwtAuthenticationFilter,
                        RateLimitFilter rateLimitFilter
                        // ApiKeyFilter apiKeyFilter
                        ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.rateLimitFilter = rateLimitFilter;
        // this.apiKeyFilter = apiKeyFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/",
                                "/home",
                                "/api/auth/**",
                                "/auth/**",
                                "/h2-console/**",
                                "/error",
                                "/favicon.ico",
                                "/api/books",
                                "/api/reservations/"
                        ).permitAll()
                        //  mais facil usar isso aq se quiser algo global
                        /*.requestMatchers("/api/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                        .requestMatchers(GET, "/api/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGEMENT_READ.name())
                        .requestMatchers(POST, "/api/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGEMENT_CREATE.name())
                        .requestMatchers(PUT, "/api/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGEMENT_UPDATE.name())
                        .requestMatchers(DELETE, "/api/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGEMENT_DELETE.name())

                        .requestMatchers("api/admin/**").hasRole(ADMIN.name())

                        .requestMatchers(GET, "/api/admin/**").hasAnyAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/admin/**").hasAnyAuthority(ADMIN_DELETE.name())*/

                        .anyRequest().authenticated()
                )
                // .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class) // Enable this line to activate API Key filter (only use on non-human clients)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                );

        return http.build();
    }
}