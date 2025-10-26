package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller for verifying endpoint access based on roles.
 * 
 * @author Nicolás Sabogal
 */
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    /** Public endpoint to verify server is running */
    @GetMapping("/public")
    public String home() {
        return "If you see this message, the server is running correctly.";
    }

    /** Endpoint accessible to users with USER role */
    @GetMapping("/user")
    public String userEndpoint() {
        return "If you see this message, you have accessed a user endpoint.";
    }

    /** Endpoint accessible only to admin users */
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "If you see this message, you have accessed an admin endpoint.";
    }
    
}
