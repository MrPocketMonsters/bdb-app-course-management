package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Application-wide configuration class.
 * 
 * @author Nicolás Sabogal
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    /** User service for managing user-related operations */
    private final UserService userService;

    /**
     * UserDetailsService bean for loading user details by email
     *
     * @return The UserDetailsService instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userService.loadUserByEmail(email);
    }

    /**
     * AuthenticationManager bean for managing authentication
     * 
     * @param configuration The authentication configuration
     * @return The AuthenticationManager instance
     * @throws Exception if an error occurs while retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
}
