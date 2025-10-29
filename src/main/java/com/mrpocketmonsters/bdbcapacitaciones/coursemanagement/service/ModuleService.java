package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewModuleRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Module;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.User;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.CourseRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.ModuleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service for Module operations.
 * Implements basic CRUD methods and maps to DTOs when appropriate.
 * 
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class ModuleService {

    /** Repository for Module entities */
    private final ModuleRepository moduleRepository;
    /** Service for User operations */
    private final UserService userService;
    /** Repository for Course entities */
    private final CourseRepository courseRepository;

    /**
     * Retrieves a paginated list of all modules.
     * 
     * @param page the page number to retrieve
     * @param size the number of modules per page
     * @return a Page of Modules
     */
    public Page<Module> getAllModules(Integer page, Integer size) {
    return moduleRepository.findAll(
        Pageable
            .ofSize(size)
            .withPage(page)
        );
    }

    /**
     * Retrieves a module by its unique identifier.
     * 
     * @param id the unique identifier of the module
     * @return the module entity
     * @throws EntityNotFoundException if the module is not found
     */
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Module not found with id " + id));
    }

    /**
     * Creates a new module.
     * 
     * @param adminEmail the email of the admin user creating the module
     * @param module the NewModuleRequest DTO containing module details
     * @return the newly created module
     */
    public Module createModule(String adminEmail, NewModuleRequest module) {
        User admin = userService.loadUserByEmail(adminEmail);
        Module entity = Module.builder()
            .name(module.getName())
            .description(module.getDescription())
            .admin(admin)
            .build();
        
        return moduleRepository.save(entity);
    }

    /**
     * Updates an existing module.
     * 
     * @param id the unique identifier of the module to update
     * @param module the NewModuleRequest DTO containing updated module details
     * @return the updated Module entity
     * @throws EntityNotFoundException if the module is not found
     */
    public Module updateModule(Long id, NewModuleRequest module) {
        Module existing = moduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Module not found with id " + id));

        existing.setName(module.getName());
        existing.setDescription(module.getDescription());
        return moduleRepository.save(existing);
    }

    /**
     * Deletes a module by its unique identifier.
     * 
     * @param id the unique identifier of the module to delete
     * @throws EntityNotFoundException if the module is not found
     */
    public void deleteModule(Long id) {
        if (!moduleRepository.existsById(id))
            throw new EntityNotFoundException("Module not found with id " + id);

        moduleRepository.deleteById(id);
    }

    /**
     * Assign courses to a module.
     * 
     * @param id the ID of the module
     * @param courseId the course identifier DTO
     */
    public void assignCoursesToModule(Long id, Long courseId) {
        Module module = moduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Module not found with id " + id));

        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + courseId));

        module.getCourses().add(course);
        moduleRepository.save(module);
    }

    /**
     * Remove a course from a module.
     * 
     * @param id the ID of the module
     * @param courseId the ID of the course to remove
     */
    public void removeCourseFromModule(Long id, Long courseId) {
        Module module = moduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Module not found with id " + id));
        
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + courseId));

        module.getCourses().remove(course);
        moduleRepository.save(module);
    }

    public Set<Course> getCoursesByModuleId(Long id) {
        Module module = moduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Module not found with id " + id));

        return module.getCourses();
    }
    
}
