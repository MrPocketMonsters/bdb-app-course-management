package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Chapter;

import lombok.Data;

/**
 * Detailed DTO for Chapter information.
 *
 * @author Nicolás Sabogal
 */
@Data
public class ChapterDetailsResponse {

    /** Course id to which the chapter belongs */
    private Long courseId;

    /** Chapter order within the course */
    private Integer order;

    /** Chapter title */
    private String name;

    /** Chapter short description */
    private String description;

    /** Chapter content */
    private String content;

    /** Creator name */
    private String createdBy;

    /**
     * Maps a Chapter entity to a ChapterDetailsResponse DTO.
     *
     * @param chapter the Chapter entity
     * @return the corresponding DTO
     */
    public static ChapterDetailsResponse of(Chapter chapter) {
        ChapterDetailsResponse dto = new ChapterDetailsResponse();
        dto.setCourseId(chapter.getId().getCourse().getId());
        dto.setOrder(chapter.getId().getOrder());
        dto.setName(chapter.getName());
        dto.setDescription(chapter.getDescription());
        dto.setContent(chapter.getContent());
        dto.setCreatedBy(chapter.getAdmin().getName());

        return dto;
    }

}
