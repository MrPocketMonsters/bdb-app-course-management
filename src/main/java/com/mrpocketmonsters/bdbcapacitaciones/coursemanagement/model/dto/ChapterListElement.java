package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Chapter;

import lombok.Data;

/**
 * DTO representing a chapter in a course.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class ChapterListElement {

    /** Unique identifier of the chapter */
    private Integer order;
    /** Title of the chapter */
    private String name;

    /** 
     * Maps a Chapter entity to a ChapterListElement DTO.
     * 
     * @param chapter the Chapter entity to map
     * @return the corresponding ChapterListElement DTO
     */
    public static ChapterListElement of(Chapter chapter) {
        ChapterListElement dto = new ChapterListElement();
        dto.setOrder(chapter.getId().getOrder());
        dto.setName(chapter.getName());
        return dto;
    }

}
