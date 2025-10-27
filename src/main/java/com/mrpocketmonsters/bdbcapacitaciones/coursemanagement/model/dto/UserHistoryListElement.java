package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import java.time.Instant;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.UserHistory;

import lombok.Data;

/**
 * DTO with basic information of a UserHistory for listing purposes.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class UserHistoryListElement {

    /** The unique identifier of the user history record. */
    private Long id;
    /** The identifier of the user. */
    private Long userId;
    /** The identifier of the course. */
    private Long courseId;
    /** The order of the chapter within the course. */
    private Integer order;
    /** The time when the user started the chapter. */
    private Instant startedAt;
    /** The time when the user completed the chapter. */
    private Instant completedAt;

    /**
     * Creates a UserHistoryListElement DTO from a UserHistory entity.
     * 
     * @param userHistory the UserHistory entity
     * @return a UserHistoryListElement DTO
     */
    public static UserHistoryListElement of(UserHistory userHistory) {
        UserHistoryListElement dto = new UserHistoryListElement();
        dto.setId(userHistory.getId());
        dto.setUserId(userHistory.getUser().getId());
        dto.setCourseId(userHistory.getChapter().getId().getCourse().getId());
        dto.setOrder(userHistory.getChapter().getId().getOrder());
        dto.setStartedAt(userHistory.getStart());
        dto.setCompletedAt(userHistory.getEnd());

        return dto;
    }
    
}
