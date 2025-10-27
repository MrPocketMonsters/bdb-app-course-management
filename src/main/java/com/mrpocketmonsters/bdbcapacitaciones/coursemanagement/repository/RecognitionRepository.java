package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Recognition;

/**
 * Repository for Recognition entities.
 *
 * @author Nicolás Sabogal
 */
public interface RecognitionRepository extends JpaRepository<Recognition, Long> {

    /**
     * Find recognitions for a given user
     */
    List<Recognition> findByUser_Id(Long userId);

    /**
     * Find a recognition by user and course
     */
    Optional<Recognition> findByUser_IdAndCourse_Id(Long userId, Long courseId);

}
