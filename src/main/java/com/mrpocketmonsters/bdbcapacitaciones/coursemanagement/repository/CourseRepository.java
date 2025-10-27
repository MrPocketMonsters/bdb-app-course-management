package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;

/**
 * Repository interface for Course entities.
 * Extends JpaRepository to provide CRUD operations and query methods.
 *
 * @author Nicolás Sabogal
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

}
