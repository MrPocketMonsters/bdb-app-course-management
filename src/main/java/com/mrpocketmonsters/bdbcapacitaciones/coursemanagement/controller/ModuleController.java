package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.CourseIdentifierDto;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ModuleDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ModuleListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewModuleRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ModuleIdentifierDto;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service.ModuleService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


/**
 * Controller for managing modules.
 * Provides endpoints for CRUD operations on modules.
 * 
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/modules")
public class ModuleController {

    /** Service for module operations */
    private final ModuleService moduleService;
    
    /**
     * Get all modules.
     * 
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return a ResponseEntity containing the list of modules or an error response
     */
    @GetMapping("/")
    public ResponseEntity<Page<ModuleListElement>> getAllModules(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(moduleService.getAllModules(page, size));
    }

    /**
     * Get a module by its ID.
     * 
     * @param id the ID of the module
     * @return a ResponseEntity containing the module information or an error response
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModuleDetailsResponse> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getModuleById(id));
    }

    /**
     * Create a new module.
     * 
     * @param module the module data
     * @return a ResponseEntity containing the created module information or an error response
     */
    @PostMapping("/")
    public ResponseEntity<ModuleIdentifierDto> newModule(@RequestBody NewModuleRequest module) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(moduleService.createModule(username, module));
    }

    /**
     * Update an existing module.
     * 
     * @param id the ID of the module to update
     * @param module the updated module data
     * @return a ResponseEntity indicating the result of the operation
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModuleDetailsResponse> updateModule(@PathVariable Long id, @RequestBody NewModuleRequest module) {
        return ResponseEntity.ok(moduleService.updateModule(id, module));
    }

    /**
     * Delete a module by its ID.
     * 
     * @param id the ID of the module to delete
     * @return a ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Assign courses to a module.
     * 
     * @param id the ID of the module
     * @param courseIdentifier the course identifier DTO
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/{id}/courses")
    public ResponseEntity<Void> assignCoursesToModule(@PathVariable Long id, @RequestBody CourseIdentifierDto courseIdentifier) {
        moduleService.assignCoursesToModule(id, courseIdentifier.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Remove a course from a module.
     * 
     * @param id the ID of the module
     * @param courseId the ID of the course to remove
     * @return a ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/{id}/courses/{courseId}")
    public ResponseEntity<Void> removeCourseFromModule(@PathVariable Long id, @PathVariable Long courseId) {
        moduleService.removeCourseFromModule(id, courseId);
        return ResponseEntity.ok().build();
    }

}
