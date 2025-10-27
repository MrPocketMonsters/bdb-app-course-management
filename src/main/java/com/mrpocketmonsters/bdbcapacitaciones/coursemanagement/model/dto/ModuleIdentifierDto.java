package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

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

}
