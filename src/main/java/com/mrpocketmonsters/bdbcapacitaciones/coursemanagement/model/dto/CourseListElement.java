package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;

import lombok.Data;

/**
 * Data Transfer Object for listing courses with minimal information.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class CourseListElement {

    /** Unique identifier for the course */
    private Long id;

    /** Name of the course */
    private String name;

    /**
     * Maps a Course entity to a CourseListElement DTO.
     * 
     * @param course the Course entity to map
     * @return the corresponding CourseListElement DTO
     */
    public static CourseListElement of(Course course) {
        CourseListElement element = new CourseListElement();
        element.setId(course.getId());
        element.setName(course.getName());
        return element;
    }
    
}
