package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.UserHistory;

/**
 * Repository interface for UserHistory entities.
 * Handles CRUD operations and data access.
 *
 * @author Nicolás Sabogal
 */
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    /**
     * Find UserHistory records by user ID.
     * 
     * @param userId the user ID
     * @return a list of UserHistory records
     */
    List<UserHistory> findByUser_Id(Long userId);

    /**
     * Find UserHistory records by user ID and course ID.
     * 
     * @param userId the user ID
     * @param courseId the course ID
     * @return a list of UserHistory records
     */
    List<UserHistory> findByUser_IdAndChapter_Id_Course_Id(Long userId, Long courseId);

}
