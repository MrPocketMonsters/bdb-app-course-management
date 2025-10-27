package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ModuleDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ModuleListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewModuleRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ModuleIdentifierDto;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Module;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.User;
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

    /**
     * Retrieves a paginated list of all modules.
     * 
     * @param page the page number to retrieve
     * @param size the number of modules per page
     * @return a Page of ModuleListElement DTOs
     */
    public Page<ModuleListElement> getAllModules(Integer page, Integer size) {
        return moduleRepository.findAll(
                Pageable
                    .ofSize(size)
                    .withPage(page)
            )
            .map(ModuleListElement::of);
    }

    /**
     * Retrieves a module by its unique identifier.
     * 
     * @param id the unique identifier of the module
     * @return a ModuleDetailsResponse DTO containing module details
     * @throws EntityNotFoundException if the module is not found
     */
    public ModuleDetailsResponse getModuleById(Long id) {
        return moduleRepository.findById(id)
            .map(ModuleDetailsResponse::of)
            .orElseThrow(
                () -> new EntityNotFoundException("Module not found with id " + id)
            );
    }

    /**
     * Creates a new module.
     * 
     * @param adminEmail the email of the admin user creating the module
     * @param module the NewModuleRequest DTO containing module details
     * @return a NewModuleResponse DTO containing the ID of the newly created module
     */
    public ModuleIdentifierDto createModule(String adminEmail, NewModuleRequest module) {
        User admin = userService.loadUserByEmail(adminEmail);
        Module entity = Module.builder()
            .name(module.getName())
            .description(module.getDescription())
            .admin(admin)
            .build();
        
        Module saved = moduleRepository.save(entity);

        ModuleIdentifierDto resp = new ModuleIdentifierDto();
        resp.setModuleId(saved.getId());
        return resp;
    }

    /**
     * Updates an existing module.
     * 
     * @param id the unique identifier of the module to update
     * @param module the NewModuleRequest DTO containing updated module details
     * @return the updated ModuleDetailsResponse DTO
     * @throws EntityNotFoundException if the module is not found
     */
    public ModuleDetailsResponse updateModule(Long id, NewModuleRequest module) {
        Module existing = moduleRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Module not found with id " + id)
            );

        existing.setName(module.getName());
        existing.setDescription(module.getDescription());
        Module saved = moduleRepository.save(existing);

        return ModuleDetailsResponse.of(saved);
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
    
}
