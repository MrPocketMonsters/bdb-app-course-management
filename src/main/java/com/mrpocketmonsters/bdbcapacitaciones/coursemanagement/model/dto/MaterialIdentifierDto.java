package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Material;

import lombok.Data;

/**
 * DTO with the identifier of a created Material.
 *
 * @author Nicolás Sabogal
 */
@Data
public class MaterialIdentifierDto {

    /** Identificador del material creado */
    private Long materialId;

    /**
     * Creates a MaterialIdentifierDto from a Material entity.
     * 
     * @param material the Material entity
     * @return a MaterialIdentifierDto
     */
    public static MaterialIdentifierDto of(Material material) {
        MaterialIdentifierDto dto = new MaterialIdentifierDto();
        dto.setMaterialId(material.getId());
        return dto;
    }

}
