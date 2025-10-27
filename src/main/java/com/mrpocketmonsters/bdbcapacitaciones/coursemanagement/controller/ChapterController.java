package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ChapterDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ChapterListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewChapterRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewChapterResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service.ChapterService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for managing chapters.
 * Provides endpoints for CRUD operations on chapters.
 *
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses/{courseId}/chapters")
public class ChapterController {

    /** Service for chapter operations */
    private final ChapterService chapterService;

    /**
     * Get all chapters for a given course.
     *
     * @param courseId id of the course
     * @param page page number
     * @param size page size
     * @return paginated list of chapters
     */
    @GetMapping("/")
    public ResponseEntity<Page<ChapterListElement>> getAllChapters(
        @PathVariable Long courseId,
        @RequestParam Integer page,
        @RequestParam Integer size
    ) {
        return ResponseEntity.ok(chapterService.getAllChapters(courseId, page, size));
    }

    /**
     * Get a chapter by course id and order.
     *
     * @param courseId id of the course
     * @param order order of the chapter
     * @return chapter details
     */
    @GetMapping("/{order}")
    public ResponseEntity<ChapterDetailsResponse> getChapterById(
        @PathVariable Long courseId,
        @PathVariable Integer order
    ) {
        return ResponseEntity.ok(chapterService.getChapterById(courseId, order));
    }

    /**
     * Create a new chapter under a course.
     *
     * @param courseId id of the course
     * @param chapter request body
     * @return created chapter identifiers
     */
    @PostMapping("/")
    public ResponseEntity<NewChapterResponse> newChapter(
        @PathVariable Long courseId,
        @RequestBody NewChapterRequest chapter
    ) {
        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(chapterService.createChapter(adminEmail, courseId, chapter));
    }

    /**
     * Update an existing chapter.
     *
     * @param courseId id of the course
     * @param order order of the chapter
     * @param chapter request body with updates
     * @return updated chapter
     */
    @PutMapping("/{order}")
    public ResponseEntity<ChapterDetailsResponse> updateChapter(
        @PathVariable Long courseId,
        @PathVariable Integer order,
        @RequestBody NewChapterRequest chapter
    ) {
        return ResponseEntity.ok(chapterService.updateChapter(courseId, order, chapter));
    }

    /**
     * Delete a chapter.
     *
     * @param courseId id of the course
     * @param order order of the chapter
     * @return empty response
     */
    @DeleteMapping("/{order}")
    public ResponseEntity<Void> deleteChapter(
        @PathVariable Long courseId,
        @PathVariable Integer order
    ) {
        chapterService.deleteChapter(courseId, order);
        return ResponseEntity.ok().build();
    }

}
