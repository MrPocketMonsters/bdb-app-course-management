package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Recognition;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.User;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.UserHistory;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.CourseRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.RecognitionRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.UserHistoryRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service class for managing UserHistory entities and recognitions.
 * Provides business logic related to user progress tracking and recognition issuance.
 *
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class UserHistoryService {

    /** Service for User operations */
    private final UserService userService;
    /** Service for Chapter operations */
    private final ChapterService chapterService;
    /** Repository for UserHistory entities */
    private final UserHistoryRepository userHistoryRepository;
    /** Repository for Recognition entities */
    private final RecognitionRepository recognitionRepository;
    /** Repository for User entities */
    private final UserRepository userRepository;
    /** Repository for Course entities */
    private final CourseRepository courseRepository;

    /**
     * Mark a chapter as seen by a user.
     *
     * @param userEmail email of the user
     * @param courseId course id
     * @param order chapter order
     */
    public void markChapterAsSeen(String userEmail, Long courseId, Integer order) {
        var chapter = chapterService.getChapterById(courseId, order);

        UserHistory userHistory = UserHistory.builder()
            .user(userService.loadUserByEmail(userEmail))
            .chapter(chapter)
            .build();

        userHistoryRepository.save(userHistory);
    }

    /**
     * Get the chapter history for a user.
     *
     * @param userId user identifier
     * @return collection of chapters seen by the user
     */
    public Collection<UserHistory> getUserChapterHistory(Long userId) {
        if (!userRepository.existsById(userId))
            throw new EntityNotFoundException("User not found with id " + userId);

        return userHistoryRepository.findByUser_Id(userId);
    }

    /**
     * Get (or generate) all recognitions for a user.
     *
     * @param userId user identifier
     * @return list of Recognition entities
     */
    public List<Recognition> getAllUserRecognitions(Long userId) {
        if (!userRepository.existsById(userId))
            throw new EntityNotFoundException("User not found with id " + userId);

        // First get current recognitions and courses in user histories.
        List<Recognition> recognitions = recognitionRepository.findByUser_Id(userId);

        List<Long> recognizedCourseIds = recognitions
            .stream()
            .map(recognition -> recognition.getCourse().getId())
            .toList();

        List<Course> coursesInUserHistories = userHistoryRepository
            .findByUser_Id(userId)
            .stream()
            .map(uh -> uh.getChapter().getId().getCourse())
            .filter(course -> !recognizedCourseIds.contains(course.getId()))
            .distinct()
            .toList();

        if (recognitions.size() == coursesInUserHistories.size())
            return recognitions;

        // If there are courses without recognition, attempt to generate them.
        for (Course course : coursesInUserHistories)
            try {
                recognitions.add(getUserRecognitionForCourse(userId, course.getId()));
            } catch (IllegalStateException exception) {
                if (!exception.getMessage().equals("User has not completed the course"))
                    throw exception;
            }

        return recognitions;
    }

    /**
     * Get (or generate) the recognition for a given user and course.
     * If a recognition exists it is returned. Otherwise, if the user has completed
     * all chapters of the course, a new recognition is created and returned.
     *
     * @param userId user identifier
     * @param courseId course identifier
     * @return the Recognition entity
     */
    public Recognition getUserRecognitionForCourse(Long userId, Long courseId) {
        if (!userRepository.existsById(userId))
            throw new EntityNotFoundException("User not found with id " + userId);

        return recognitionRepository.findByUser_IdAndCourse_Id(userId, courseId)
            .orElseGet(() -> generateNewRecognition(userId, courseId));
    }

    /**
     * Generate a new recognition for a user who has completed a course.
     *
     * @param userId user identifier
     * @param courseId course identifier
     * @return the newly created Recognition entity
     */
    public Recognition generateNewRecognition(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + courseId));

        if (!hasUserCompletedCourse(user, course)) {
            throw new IllegalStateException("User has not completed the course");
        }

        Recognition recognition = Recognition.builder()
            .user(user)
            .course(course)
            .createdAt(Instant.now())
            .build();

        return recognitionRepository.save(recognition);
    }

    /**
     * Check if a user has completed all chapters of a course.
     *
     * @param user the User entity
     * @param course the Course entity
     * @return true if the user has completed the course, false otherwise
     */
    private boolean hasUserCompletedCourse(User user, Course course) {
        // Find all chapters seen by the user for the course
        var histories = userHistoryRepository
            .findByUser_IdAndChapter_Id_Course_Id(user.getId(), course.getId());

        // assign the number of chapters in the course or 0 if none.
        int totalChapters = course.getChapters() == null ? 0 : course.getChapters().size();

        return totalChapters > 0 && histories.size() >= totalChapters;
    }
}
