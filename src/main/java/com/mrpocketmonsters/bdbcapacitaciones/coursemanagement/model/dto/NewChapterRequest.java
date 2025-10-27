package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import lombok.Data;

/**
 * DTO for creating/updating a Chapter.
 *
 * @author Nicolás Sabogal
 */
@Data
public class NewChapterRequest {

    /** Order of the chapter within the course */
    private Integer order;

    /** Title of the chapter */
    private String name;

    /** Short description of the chapter */
    private String description;

    /** Full content of the chapter (markdown/html/plaintext) */
    private String content;

}
