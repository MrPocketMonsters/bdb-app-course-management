package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Role;

import lombok.RequiredArgsConstructor;

/**
 * Security configuration class.
 * Here we define the security filter chain and related security settings.
 * 
 * @author Nicolás Sabogal
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    /** CorsConfigurationSource for handling CORS settings */
    private final CorsConfigurationSource corsConfigurationSource;

    /** JWT Authentication Filter for processing JWT tokens */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow access to Swagger UI and API docs without authentication
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Test endpoints access rules
                .requestMatchers("/api/v1/test/public").permitAll()
                .requestMatchers("/api/v1/test/user").hasAuthority(Role.USER.name())
                .requestMatchers("/api/v1/test/admin").hasAuthority(Role.ADMIN.name())
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(sessionManager -> sessionManager
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    
}
