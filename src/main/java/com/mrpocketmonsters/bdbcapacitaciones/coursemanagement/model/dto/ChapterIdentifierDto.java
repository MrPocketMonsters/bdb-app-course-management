package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import lombok.Data;

/**
 * DTO representing the response after creating a new chapter.
 * Contains identifiers to locate the created chapter.
 *
 * @author Nicolás Sabogal
 */
@Data
public class ChapterIdentifierDto {

    /** Course id where the chapter was created */
    private Long courseId;

    /** Order of the created chapter within the course */
    private Integer order;

}
