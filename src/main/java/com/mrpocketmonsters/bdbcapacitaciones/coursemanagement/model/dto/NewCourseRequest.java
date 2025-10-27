package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import lombok.Data;

/**
 * DTO para creación/actualización de Course.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class NewCourseRequest {

    /** Name of the course */
    private String name;
    /** Description of the course */
    private String description;
    /** Duration of the course in minutes */
    private Integer durationInMinutes;
    /** External URL for the course */
    private String externalUrl;

}
