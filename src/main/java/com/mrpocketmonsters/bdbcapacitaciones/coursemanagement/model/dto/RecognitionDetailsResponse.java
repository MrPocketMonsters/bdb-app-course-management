package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import java.time.Instant;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Recognition;

import lombok.Data;

/**
 * DTO with detailed information of a Recognition.
 *
 * @author Nicolás Sabogal
 */
@Data
public class RecognitionDetailsResponse {

    /** The unique identifier of the recognition. */
    private Long id;
    /** The course id associated with the recognition. */
    private Long courseId;
    /** The course name associated with the recognition. */
    private String courseName;
    /** The identifier of the user who received the recognition. */
    private Long userId;
    /** The time when the recognition was created. */
    private Instant createdAt;

    public static RecognitionDetailsResponse of(Recognition recognition) {
        RecognitionDetailsResponse dto = new RecognitionDetailsResponse();
        dto.setId(recognition.getId());
        dto.setCourseId(recognition.getCourse().getId());
        dto.setCourseName(recognition.getCourse().getName());
        dto.setUserId(recognition.getUser().getId());
        dto.setCreatedAt(recognition.getCreatedAt());
        return dto;
    }

}
