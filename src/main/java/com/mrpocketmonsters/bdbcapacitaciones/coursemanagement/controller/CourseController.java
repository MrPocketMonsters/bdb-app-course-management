package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewCourseRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.CourseIdentifierDto;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.CourseDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.CourseListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service.CourseService;

/**
 * Controller for managing courses.
 * Provides endpoints for CRUD operations on courses.
 * 
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    /** Service for course operations */
    private final CourseService courseService;

    /**
     * Get all courses.
     * 
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return a ResponseEntity containing the list of courses or an error response
     */
    @GetMapping("/")
    public ResponseEntity<Page<CourseListElement>> getAllCourses(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(
            courseService.getAllCourses(page, size)
                .map(CourseListElement::of)
        );
    }

    /**
     * Get a course by its ID.
     * 
     * @param id the ID of the course
     * @return a ResponseEntity containing the course information or an error response
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailsResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(CourseDetailsResponse.of(courseService.getCourseById(id)));
    }

    /**
     * Create a new course.
     * 
     * @param course the course data
     * @return a ResponseEntity containing the created course information or an error response
     */
    @PostMapping("/")
    public ResponseEntity<CourseIdentifierDto> newCourse(@RequestBody NewCourseRequest course) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(CourseIdentifierDto.of(courseService.createCourse(username, course)));
    }

    /**
     * Update an existing course.
     * 
     * @param id the ID of the course to update
     * @param course the updated course data
     * @return a ResponseEntity containing the updated course information or an error response
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseDetailsResponse> updateCourse(@PathVariable Long id, @RequestBody NewCourseRequest course) {
        return ResponseEntity.ok(CourseDetailsResponse.of(courseService.updateCourse(id, course)));
    }

    /**
     * Delete a course by its ID.
     * 
     * @param id the ID of the course to delete
     * @return a ResponseEntity indicating the result of the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

}
