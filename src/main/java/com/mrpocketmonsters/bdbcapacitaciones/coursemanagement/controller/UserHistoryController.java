package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.RecognitionDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.RecognitionListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.UserHistoryListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service.UserHistoryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * REST controller for managing UserHistory entities.
 * Provides endpoints for user progress tracking.
 *
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/userhistories/{userId}")
public class UserHistoryController {

    /** Service for UserHistory operations */
    private final UserHistoryService userHistoryService;

    /**
     * Get the chapter history for a user.
     *
     * @param userId user identifier
     * @return collection of chapters seen by the user
     */
    @GetMapping("/")
    public ResponseEntity<List<UserHistoryListElement>> getUserChapterHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(
            userHistoryService.getUserChapterHistory(userId)
                .stream()
                .map(UserHistoryListElement::of)
                .toList()
        );
    }

    /**
     * Get all recognitions for a user.
     *
     * @param userId user identifier
     * @return collection of recognitions earned by the user
     */
    @GetMapping("/courses")
    public ResponseEntity<List<RecognitionListElement>> getAllUserRecognitions(@PathVariable Long userId) {
        return ResponseEntity.ok(
            userHistoryService.getAllUserRecognitions(userId)
                .stream()
                .map(RecognitionListElement::of)
                .toList()
        );
    }

    /**
     * Get recognition details for a user in a specific course.
     *
     * @param userId   user identifier
     * @param courseId course identifier
     * @return recognition details for the specified course
     */
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<RecognitionDetailsResponse> getUserRecognitionForCourse(
        @PathVariable Long userId,
        @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(
            RecognitionDetailsResponse.of(
                userHistoryService.getUserRecognitionForCourse(userId, courseId)
            )
        );
    }
    
}
