package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import java.util.List;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;

import lombok.Data;

/**
 * DTO with detailed Course information.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class CourseDetailsResponse {

    /** Unique identifier of the course */
    private Long id;
    
    /** Name of the course */
    private String name;

    /** Description of the course */
    private String description;

    /** Duration of the course in minutes */
    private Integer durationInMinutes;

    /** External URL for the course */
    private String externalUrl;

    /** List of chapters in the course */
    private List<ChapterListElement> chapters;

    /** Creator of the course */
    private String createdBy;

    /**
     * Maps a Course entity to a CourseDetailsResponse DTO.
     * 
     * @param course the Course entity to map
     * @return the corresponding CourseDetailsResponse DTO
     */
    public static CourseDetailsResponse of(Course course) {
        CourseDetailsResponse dto = new CourseDetailsResponse();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setDurationInMinutes(course.getDurationInMinutes());
        dto.setExternalUrl(course.getExternalUrl());
        dto.setChapters(
            course.getChapters().stream()
                .map(ChapterListElement::of)
                .toList()
        );
        dto.setCreatedBy(course.getAdmin().getName());

        return dto;
    }
}
