package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import lombok.Data;

/**
 * DTO returned after creating/updating a Course.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class NewCourseResponse {

    /** Unique identifier of the created course */
    private Long courseId;

}
