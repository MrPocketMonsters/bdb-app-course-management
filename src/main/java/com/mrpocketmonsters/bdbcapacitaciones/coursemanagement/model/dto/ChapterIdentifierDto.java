package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Chapter;

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

    /**
     * Creates a ChapterIdentifierDto from a Chapter entity.
     * 
     * @param chapter the Chapter entity
     * @return a ChapterIdentifierDto
     */
    public static ChapterIdentifierDto of(Chapter chapter) {
        ChapterIdentifierDto dto = new ChapterIdentifierDto();
        dto.setCourseId(chapter.getId().getCourse().getId());
        dto.setOrder(chapter.getId().getOrder());
        return dto;
    }

}
