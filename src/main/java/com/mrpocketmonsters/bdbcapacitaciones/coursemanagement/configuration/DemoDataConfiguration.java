package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.configuration;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Chapter;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.ChapterId;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Course;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Material;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.MaterialType;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Module;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Recognition;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.User;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.UserHistory;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.ChapterRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.CourseRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.MaterialRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.ModuleRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.RecognitionRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.UserHistoryRepository;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for creating demo data.
 * This class defines beans that initialize demo data for testing or development purposes.
 * 
 * @author Nicolás Sabogal
 */
@Configuration
@RequiredArgsConstructor
public class DemoDataConfiguration {

    /** Repository for managing user entities */
    private final UserRepository userRepository;
    /** Repository for managing course entities */
    private final CourseRepository courseRepository;
    /** Repository for managing module entities */
    private final ModuleRepository moduleRepository;
    /** Repository for managing material entities */
    private final MaterialRepository materialRepository;
    /** Repository for managing chapter entities */
    private final ChapterRepository chapterRepository;
    /** Repository for managing user history entities */
    private final UserHistoryRepository userHistoryRepository;
    /** Repository for managing recognition entities */
    private final RecognitionRepository recognitionRepository;

    /** Flag to ensure demo data is only initialized once */
    private volatile boolean initialized = false;

    /** 
     * CommandLineRunner bean to create demo data at application startup.
     * Drops all existing data and recreates demo data.
     * Uses a flag to ensure execution happens only once.
     * 
     * @return CommandLineRunner that initializes demo data
     */
    @Bean
    public CommandLineRunner createDemoDataIfNotExists() {
        return args -> {
            // Check if already initialized
            if (initialized) {
                System.out.println("Demo data initialization already executed. Skipping.");
                return;
            }

            if (moduleRepository.count() > 0 || courseRepository.count() > 0) {
                System.out.println("Database already contains data. Skipping demo data initialization.");
                initialized = true;
                return;
            }
            
            System.out.println("Initializing demo data...");

            // Assume user with ID 1 exists
            User admin = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User with ID 1 not found. Please create it first."));

            // Create Materials
            Material videoMaterial1 = Material.builder()
                .name("Introducción a Spring Boot")
                .description("Video introductorio sobre Spring Boot framework")
                .type(MaterialType.VIDEO)
                .url("https://www.youtube.com/watch?v=spring-boot-intro")
                .build();

            Material videoMaterial2 = Material.builder()
                .name("Seguridad en Spring")
                .description("Tutorial sobre Spring Security")
                .type(MaterialType.VIDEO)
                .url("https://www.youtube.com/watch?v=spring-security")
                .build();

            Material documentMaterial1 = Material.builder()
                .name("Guía de Spring Data JPA")
                .description("Documento completo sobre Spring Data JPA")
                .type(MaterialType.DOCUMENT)
                .url("https://docs.spring.io/spring-data/jpa/docs/current/reference/html/")
                .build();

            Material documentMaterial2 = Material.builder()
                .name("Best Practices en Java")
                .description("Mejores prácticas de programación en Java")
                .type(MaterialType.DOCUMENT)
                .url("https://example.com/java-best-practices.pdf")
                .build();

            Material testMaterial1 = Material.builder()
                .name("Examen Final Spring Boot")
                .description("Evaluación final del curso de Spring Boot")
                .type(MaterialType.TEST)
                .url("https://example.com/test/spring-boot-final")
                .build();

            Material testMaterial2 = Material.builder()
                .name("Quiz Intermedio")
                .description("Evaluación intermedia de conocimientos")
                .type(MaterialType.TEST)
                .url("https://example.com/test/intermediate-quiz")
                .build();

            materialRepository.save(videoMaterial1);
            materialRepository.save(videoMaterial2);
            materialRepository.save(documentMaterial1);
            materialRepository.save(documentMaterial2);
            materialRepository.save(testMaterial1);
            materialRepository.save(testMaterial2);

            // Create Courses
            Course course1 = Course.builder()
                .name("Desarrollo con Spring Boot")
                .description("Curso completo sobre desarrollo de aplicaciones con Spring Boot framework")
                .durationInMinutes(480)
                .externalUrl("https://example.com/courses/spring-boot")
                .admin(admin)
                .build();

            Course course2 = Course.builder()
                .name("Arquitectura de Microservicios")
                .description("Aprende a diseñar y desarrollar sistemas basados en microservicios")
                .durationInMinutes(600)
                .externalUrl("https://example.com/courses/microservices")
                .admin(admin)
                .build();

            Course course3 = Course.builder()
                .name("Java Avanzado")
                .description("Conceptos avanzados de programación en Java")
                .durationInMinutes(720)
                .externalUrl("https://example.com/courses/advanced-java")
                .admin(admin)
                .build();

            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);

            // Create Chapters for Course 1
            Chapter chapter1_1 = Chapter.builder()
                .id(ChapterId.builder()
                    .course(course1)
                    .order(1)
                    .build())
                .name("Introducción")
                .description("Conceptos básicos de Spring Boot")
                .content("# Introducción a Spring Boot\n\nSpring Boot es un framework que facilita el desarrollo de aplicaciones Java...")
                .admin(admin)
                .materials(new HashSet<>(Set.of(videoMaterial1, documentMaterial1)))
                .build();

            Chapter chapter1_2 = Chapter.builder()
                .id(ChapterId.builder()
                    .course(course1)
                    .order(2)
                    .build())
                .name("Configuración")
                .description("Configuración de proyectos Spring Boot")
                .content("# Configuración\n\nEn este capítulo aprenderás a configurar tu proyecto Spring Boot...")
                .admin(admin)
                .materials(new HashSet<>(Set.of(documentMaterial1)))
                .build();

            Chapter chapter1_3 = Chapter.builder()
                .id(ChapterId.builder()
                    .course(course1)
                    .order(3)
                    .build())
                .name("Seguridad")
                .description("Implementación de seguridad en Spring Boot")
                .content("# Seguridad en Spring Boot\n\nLa seguridad es fundamental en cualquier aplicación...")
                .admin(admin)
                .materials(new HashSet<>(Set.of(videoMaterial2)))
                .build();

            Chapter chapter1_4 = Chapter.builder()
                .id(ChapterId.builder()
                    .course(course1)
                    .order(4)
                    .build())
                .name("Evaluación Final")
                .description("Examen final del curso")
                .content("# Evaluación Final\n\nDemuestra lo que has aprendido en este curso...")
                .admin(admin)
                .materials(new HashSet<>(Set.of(testMaterial1)))
                .build();

            chapterRepository.save(chapter1_1);
            chapterRepository.save(chapter1_2);
            chapterRepository.save(chapter1_3);
            chapterRepository.save(chapter1_4);

            // Create Chapters for Course 2
            Chapter chapter2_1 = Chapter.builder()
                .id(ChapterId.builder()
                    .course(course2)
                    .order(1)
                    .build())
                .name("Fundamentos de Microservicios")
                .description("Conceptos básicos de arquitectura de microservicios")
                .content("# Fundamentos de Microservicios\n\nLos microservicios son una arquitectura que divide las aplicaciones...")
                .admin(admin)
                .materials(new HashSet<>(Set.of(documentMaterial2)))
                .build();

            Chapter chapter2_2 = Chapter.builder()
                .id(ChapterId.builder()
                    .course(course2)
                    .order(2)
                    .build())
                .name("Comunicación entre Servicios")
                .description("Patrones de comunicación en microservicios")
                .content("# Comunicación entre Servicios\n\nLa comunicación efectiva entre microservicios es crucial...")
                .admin(admin)
                .materials(new HashSet<>(Set.of(testMaterial2)))
                .build();

            chapterRepository.save(chapter2_1);
            chapterRepository.save(chapter2_2);

            // Create Chapters for Course 3
            Chapter chapter3_1 = Chapter.builder()
                .id(ChapterId.builder()
                    .course(course3)
                    .order(1)
                    .build())
                .name("Programación Funcional en Java")
                .description("Introducción a la programación funcional")
                .content("# Programación Funcional en Java\n\nJava 8 introdujo características de programación funcional...")
                .admin(admin)
                .materials(new HashSet<>(Set.of(documentMaterial2, videoMaterial1)))
                .build();

            chapterRepository.save(chapter3_1);

            // Create Modules
            Module module1 = Module.builder()
                .name("Módulo Backend Fundamental")
                .description("Módulo enfocado en desarrollo backend con Spring")
                .admin(admin)
                .courses(new HashSet<>(Set.of(course1, course2)))
                .build();

            Module module2 = Module.builder()
                .name("Módulo Java Profesional")
                .description("Módulo para dominar Java a nivel profesional")
                .admin(admin)
                .courses(new HashSet<>(Set.of(course1, course3)))
                .build();

            Module module3 = Module.builder()
                .name("Módulo Arquitectura de Software")
                .description("Módulo sobre arquitectura y diseño de sistemas")
                .admin(admin)
                .courses(new HashSet<>(Set.of(course2, course3)))
                .build();

            moduleRepository.save(module1);
            moduleRepository.save(module2);
            moduleRepository.save(module3);

            // Create User History (assuming the user with ID 1 has consumed some chapters)
            UserHistory history1 = UserHistory.builder()
                .user(admin)
                .chapter(chapter1_1)
                .start(Instant.now().minusSeconds(7200)) // 2 hours ago
                .end(Instant.now().minusSeconds(5400)) // 1.5 hours ago
                .build();

            UserHistory history2 = UserHistory.builder()
                .user(admin)
                .chapter(chapter1_2)
                .start(Instant.now().minusSeconds(5400)) // 1.5 hours ago
                .end(Instant.now().minusSeconds(3600)) // 1 hour ago
                .build();

            UserHistory history3 = UserHistory.builder()
                .user(admin)
                .chapter(chapter1_3)
                .start(Instant.now().minusSeconds(3600)) // 1 hour ago
                .end(Instant.now().minusSeconds(1800)) // 30 minutes ago
                .build();

            UserHistory history4 = UserHistory.builder()
                .user(admin)
                .chapter(chapter1_4)
                .start(Instant.now().minusSeconds(1800)) // 30 minutes ago
                .end(Instant.now().minusSeconds(600)) // 10 minutes ago
                .build();

            UserHistory history5 = UserHistory.builder()
                .user(admin)
                .chapter(chapter2_1)
                .start(Instant.now().minusSeconds(300)) // 5 minutes ago
                .end(null) // Currently in progress
                .build();

            userHistoryRepository.save(history1);
            userHistoryRepository.save(history2);
            userHistoryRepository.save(history3);
            userHistoryRepository.save(history4);
            userHistoryRepository.save(history5);

            // Create Recognitions (user completed course 1)
            Recognition recognition1 = Recognition.builder()
                .user(admin)
                .course(course1)
                .createdAt(Instant.now().minusSeconds(600)) // 10 minutes ago
                .build();

            recognitionRepository.save(recognition1);

            // Mark as initialized
            initialized = true;

            System.out.println("Demo data created");
            System.out.println("Summary:");
            System.out.println("  - Materials: 6");
            System.out.println("  - Courses: 3");
            System.out.println("  - Chapters: 7");
            System.out.println("  - Modules: 3");
            System.out.println("  - User History Records: 5");
            System.out.println("  - Recognitions: 1");
        };
    }
    
}
