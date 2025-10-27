package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.CourseDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.CourseListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewCourseRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewCourseResponse;
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
    public Page<CourseListElement> getAllCourses(Integer page, Integer size) {
        return courseRepository.findAll(
                Pageable
                    .ofSize(size)
                    .withPage(page)
            )
            .map(CourseListElement::of);
    }

    /**
     * Retrieves a course by its unique identifier.
     *
     * @param id the unique identifier of the course
     * @return the Course entity
     * @throws EntityNotFoundException if not found
     */
    public CourseDetailsResponse getCourseById(Long id) {
        return courseRepository.findById(id)
            .map(CourseDetailsResponse::of)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
    }

    /**
     * Creates a new course.
     *
     * @param adminEmail the email of the admin creating the course
     * @param course the NewCourseRequest DTO
     * @return NewCourseResponse with created id
     */
    public NewCourseResponse createCourse(String adminEmail, NewCourseRequest course) {
        User admin = userService.loadUserByEmail(adminEmail);

        Course entity = Course.builder()
            .name(course.getName())
            .description(course.getDescription())
            .durationInMinutes(course.getDurationInMinutes())
            .externalUrl(course.getExternalUrl())
            .admin(admin)
            .build();

        Course saved = courseRepository.save(entity);

        NewCourseResponse resp = new NewCourseResponse();
        resp.setCourseId(saved.getId());
        return resp;
    }

    /**
     * Updates an existing course.
     *
     * @param id the id of the course to update
     * @param course the NewCourseRequest DTO with updated fields
     * @return NewCourseResponse with updated id
     */
    public CourseDetailsResponse updateCourse(Long id, NewCourseRequest course) {
        Course existing = courseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));

        existing.setName(course.getName());
        existing.setDescription(course.getDescription());
        existing.setDurationInMinutes(course.getDurationInMinutes());
        existing.setExternalUrl(course.getExternalUrl());

        Course saved = courseRepository.save(existing);

        return CourseDetailsResponse.of(saved);
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
