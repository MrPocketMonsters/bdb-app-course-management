package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Material;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.MaterialType;

import lombok.Data;

/**
 * DTO with detailed information of a Material.
 *
 * @author Nicolás Sabogal
 */
@Data
public class MaterialDetailsResponse {

    /** Identificador del material */
    private Long id;
    /** Nombre del material */
    private String name;
    /** Descripción del material */
    private String description;
    /** Tipo del material */
    private MaterialType type;
    /** URL del material */
    private String url;

    /**
     * Creates a MaterialDetailsResponse DTO from a Material entity.
     * 
     * @param material the Material entity
     * @return a MaterialDetailsResponse DTO
     */
    public static MaterialDetailsResponse of(Material material) {
        MaterialDetailsResponse resp = new MaterialDetailsResponse();
        resp.setId(material.getId());
        resp.setName(material.getName());
        resp.setDescription(material.getDescription());
        resp.setType(material.getType());
        resp.setUrl(material.getUrl());

        return resp;
    }

}
