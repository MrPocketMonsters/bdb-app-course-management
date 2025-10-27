package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ChapterDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.ChapterListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewChapterRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewChapterResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Chapter;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.ChapterId;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.User;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.ChapterRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service for Chapter operations.
 * Implements basic CRUD methods and maps to DTOs when appropriate.
 *
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class ChapterService {

    /** Repository for Chapter entities */
    private final ChapterRepository chapterRepository;

    /** Repository for Course entities */
    private final CourseRepository courseRepository;

    /** Service for User operations */
    private final UserService userService;

    /**
     * Retrieves a paginated list of chapters that belong to a course.
     *
     * @param courseId id of the course
     * @param page page number
     * @param size page size
     * @return a Page of ChapterListElement DTOs
     */
    public Page<ChapterListElement> getAllChapters(Long courseId, Integer page, Integer size) {
        return chapterRepository.findById_Course_Id(
                courseId,
                Pageable.ofSize(size).withPage(page)
            )
            .map(ChapterListElement::of);
    }

    /**
     * Retrieves a chapter by course id and order.
     *
     * @param courseId the id of the course
     * @param order the order of the chapter
     * @return a ChapterDetailsResponse DTO
     * @throws EntityNotFoundException if not found
     */
    public ChapterDetailsResponse getChapterById(Long courseId, Integer order) {
        return chapterRepository.findById_Course_IdAndId_Order(courseId, order)
            .map(ChapterDetailsResponse::of)
            .orElseThrow(() -> new EntityNotFoundException("Chapter not found for course " + courseId + " and order " + order));
    }

    /**
     * Creates a new chapter under the given course.
     *
     * @param adminEmail email of the admin creating the chapter
     * @param courseId id of the course
     * @param chapter request DTO
     * @return response DTO with identifiers of created chapter
     */
    public NewChapterResponse createChapter(String adminEmail, Long courseId, NewChapterRequest chapter) {
        User admin = userService.loadUserByEmail(adminEmail);
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + courseId));

        ChapterId id = ChapterId.builder()
            .course(course)
            .order(chapter.getOrder())
            .build();

        Chapter entity = Chapter.builder()
            .id(id)
            .name(chapter.getName())
            .description(chapter.getDescription())
            .content(chapter.getContent())
            .admin(admin)
            .build();

        Chapter saved = chapterRepository.save(entity);

        NewChapterResponse resp = new NewChapterResponse();
        resp.setCourseId(saved.getId().getCourse().getId());
        resp.setOrder(saved.getId().getOrder());
        return resp;
    }

    /**
     * Updates an existing chapter.
     *
     * @param courseId id of the course
     * @param order order of the chapter
     * @param chapter request DTO with updated fields
     * @return updated chapter DTO
     */
    public ChapterDetailsResponse updateChapter(Long courseId, Integer order, NewChapterRequest chapter) {
        Chapter existing = chapterRepository.findById_Course_IdAndId_Order(courseId, order)
            .orElseThrow(() -> new EntityNotFoundException("Chapter not found for course " + courseId + " and order " + order));

        existing.setName(chapter.getName());
        existing.setDescription(chapter.getDescription());
        existing.setContent(chapter.getContent());

        Chapter saved = chapterRepository.save(existing);
        return ChapterDetailsResponse.of(saved);
    }

    /**
     * Deletes a chapter by course id and order.
     *
     * @param courseId id of the course
     * @param order order of the chapter
     */
    public void deleteChapter(Long courseId, Integer order) {
        ChapterId id = ChapterId.builder()
            .course(courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + courseId))
            )
            .order(order)
            .build();

        if (!chapterRepository.existsById(id))
            throw new EntityNotFoundException("Chapter not found for course " + courseId + " and order " + order);

        chapterRepository.deleteById(id);
    }

}
