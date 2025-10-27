package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Module;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a module element in a list.
 * Contains only the essential information needed for listing modules.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class ModuleListElement {

    /** The unique identifier of the module */
    private Long id;
    /** The name of the module */
    private String name;

    /**
     * Creates a ModuleListElement DTO from a Module entity.
     * 
     * @param module the Module entity
     * @return the corresponding ModuleListElement DTO
     */
    public static ModuleListElement of(Module module) {
        ModuleListElement dto = new ModuleListElement();
        dto.id = module.getId();
        dto.name = module.getName();
        return dto;
    }
    
}
