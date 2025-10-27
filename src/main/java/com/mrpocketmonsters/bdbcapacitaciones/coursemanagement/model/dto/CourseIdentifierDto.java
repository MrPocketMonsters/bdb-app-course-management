package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;

import lombok.Data;

/**
 * DTO returned after creating/updating a Course.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class CourseIdentifierDto {

    /** Unique identifier of the created course */
    private Long courseId;

    /**
     * Creates a CourseIdentifierDto from a Course entity.
     * 
     * @param course the Course entity
     * @return a CourseIdentifierDto
     */
    public static CourseIdentifierDto of(Course course) {
        CourseIdentifierDto dto = new CourseIdentifierDto();
        dto.setCourseId(course.getId());
        return dto;
    }

}
