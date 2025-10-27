package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.MaterialType;

import lombok.Data;

/**
 * DTO para la creación/actualización de un Material.
 * Sigue el mismo patrón que los DTOs de Module.
 *
 * @author Nicolás Sabogal
 */
@Data
public class NewMaterialRequest {

    /** Nombre del material */
    private String name;

    /** Descripción del material */
    private String description;

    /** Tipo del material */
    private MaterialType type;

    /** URL del material */
    private String url;

}
