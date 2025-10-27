package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Material;

import lombok.Data;

/**
 * DTO with basic information of a Material for listing purposes.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class MaterialListElement {

    /** Identificador del material */
    private Long id;
    /** Nombre del material */
    private String name;
    /** URL del material */
    private String url;

    /**
     * Creates a MaterialListElement DTO from a Material entity.
     * 
     * @param material the Material entity
     * @return a MaterialListElement DTO
     */
    public static MaterialListElement of(Material material) {
        MaterialListElement dto = new MaterialListElement();
        dto.setId(material.getId());
        dto.setName(material.getName());
        dto.setUrl(material.getUrl());

        return dto;
    }

}
