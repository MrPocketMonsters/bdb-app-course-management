package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Chapter;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.ChapterId;

/**
 * Repository interface for Chapter entities.
 * Extends JpaRepository to provide CRUD operations and query methods.
 *
 * @author Nicolás Sabogal
 */
public interface ChapterRepository extends JpaRepository<Chapter, ChapterId> {

    /**
     * Finds chapters that belong to a given course (by course id) with pagination.
     *
     * @param courseId the id of the course
     * @param pageable the pagination information
     * @return a page of chapters for the course
     */
    Page<Chapter> findById_Course_Id(Long courseId, Pageable pageable);

    /**
     * Finds a chapter by course id and chapter order.
     *
     * @param courseId the id of the course
     * @param order the order of the chapter within the course
     * @return the matching chapter, if any
     */
    Optional<Chapter> findById_Course_IdAndId_Order(Long courseId, Integer order);

}
