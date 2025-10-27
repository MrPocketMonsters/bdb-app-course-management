package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.User;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service for managing user-related operations.
 * 
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /** User repository for accessing user data */
    private final UserRepository userRepository;

    /**
     * Loads a user by their email.
     * 
     * @param email The email of the user to load.
     * @return The UserDetails of the found user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
    }

}
