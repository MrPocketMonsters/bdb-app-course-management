package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewCourseRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.User;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service for Course operations.
 * Implements basic CRUD methods and maps to DTOs when appropriate.
 *
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    /**
     * Retrieves a paginated list of all courses.
     *
     * @param pageRequest pagination information
     * @return a Page of CourseListElement DTOs
     */
    public Page<Course> getAllCourses(Integer page, Integer size) {
    return courseRepository.findAll(
        Pageable
            .ofSize(size)
            .withPage(page)
        );
    }

    /**
     * Retrieves a course by its unique identifier.
     *
     * @param id the unique identifier of the course
     * @return the Course entity
     * @throws EntityNotFoundException if not found
     */
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
    }

    /**
     * Creates a new course.
     *
     * @param adminEmail the email of the admin creating the course
     * @param course the NewCourseRequest DTO
     * @return Created course entity
     */
    public Course createCourse(String adminEmail, NewCourseRequest course) {
        User admin = userService.loadUserByEmail(adminEmail);

        Course entity = Course.builder()
            .name(course.getName())
            .description(course.getDescription())
            .durationInMinutes(course.getDurationInMinutes())
            .externalUrl(course.getExternalUrl())
            .admin(admin)
            .build();

        return courseRepository.save(entity);
    }

    /**
     * Updates an existing course.
     *
     * @param id the id of the course to update
     * @param course the NewCourseRequest DTO with updated fields
     * @return NewCourseResponse with updated id
     */
    public Course updateCourse(Long id, NewCourseRequest course) {
        Course existing = courseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));

        existing.setName(course.getName());
        existing.setDescription(course.getDescription());
        existing.setDurationInMinutes(course.getDurationInMinutes());
        existing.setExternalUrl(course.getExternalUrl());

        return courseRepository.save(existing);
    }

    /**
     * Deletes a course by id.
     *
     * @param id the id to delete
     */
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id))
            throw new EntityNotFoundException("Course not found with id " + id);

        courseRepository.deleteById(id);
    }

}
