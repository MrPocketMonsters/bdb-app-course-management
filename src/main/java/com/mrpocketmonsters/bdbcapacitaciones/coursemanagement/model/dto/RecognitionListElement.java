package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import java.time.Instant;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Recognition;

import lombok.Data;

/**
 * DTO for listing recognitions.
 *
 * @author Nicolás Sabogal
 */
@Data
public class RecognitionListElement {

    /** The unique identifier of the recognition. */
    private Long id;
    /** The identifier of the course associated with the recognition. */
    private Long courseId;
    /** The name of the course associated with the recognition. */
    private String courseName;
    /** The time when the recognition was created. */
    private Instant createdAt;

    /**
     * Creates a RecognitionListElement DTO from a Recognition entity.
     * 
     * @param recognition the Recognition entity
     * @return a RecognitionListElement DTO
     */
    public static RecognitionListElement of(Recognition recognition) {
        RecognitionListElement dto = new RecognitionListElement();
        dto.setId(recognition.getId());
        dto.setCourseId(recognition.getCourse().getId());
        dto.setCourseName(recognition.getCourse().getName());
        dto.setCreatedAt(recognition.getCreatedAt());

        return dto;
    }

}
