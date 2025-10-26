package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a recognition (reconocimiento) issued to a user
 * after completing a course.
 *
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recognition")
public class Recognition {

    /** Unique identifier for the recognition */
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "seq_recognition"
    )
    @SequenceGenerator(
        name = "seq_recognition",
        sequenceName = "seq_recognition",
        allocationSize = 1
    )
    @NotNull
    @Column(
        name = "id_recognition", 
        nullable = false
    )
    private Long id;

    /** User who received the recognition */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_recognition", 
        nullable = false
    )
    private User user;

    /** Course for which the recognition was issued */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "course_recognition",
        nullable = false
    )
    private Course course;

    /** Timestamp when the recognition was created/emitted */
    @NotNull
    @Column(
        name = "createdat_recognition",
        nullable = false,
        updatable = false
    )
    private Instant createdAt;
    
}
