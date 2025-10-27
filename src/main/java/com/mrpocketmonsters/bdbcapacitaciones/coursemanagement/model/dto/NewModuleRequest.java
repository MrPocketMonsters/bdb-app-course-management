package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for module creation requests.
 * Contains the necessary information to create a new module.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class NewModuleRequest {

    /** Name of the module */
    private String name;
    /** Description of the module */
    private String description;
    
}
