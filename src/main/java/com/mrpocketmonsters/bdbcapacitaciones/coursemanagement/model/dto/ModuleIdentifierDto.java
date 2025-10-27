package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Module;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the response after creating a new module.
 * Contains the unique identifier of the newly created module.
 * 
 * @author Nicolás Sabogal
 */
@Data   
public class ModuleIdentifierDto {

    /** Unique identifier of the newly created module */
    private Long moduleId;

    /**
     * Creates a ModuleIdentifierDto with the given module ID.
     * 
     * @param moduleId the unique identifier of the module
     * @return a ModuleIdentifierDto
     */
    public static ModuleIdentifierDto of(Module module) {
        ModuleIdentifierDto dto = new ModuleIdentifierDto();
        dto.setModuleId(module.getId());
        return dto;
    }

}
