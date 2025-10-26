package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a course in the course management system.
 * Maps to the "course" table in the database.
 * 
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "course",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Course {

    /** Unique identifier for the course */
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "seq_course"
    )
    @SequenceGenerator(
        name = "seq_course",
        sequenceName = "seq_course",
        allocationSize = 1
    )
    @NotNull
    @Column(
        name = "id_course",
        nullable = false
    )
    private Long id;

    /** Course name / title */
    @NotEmpty
    @Column(
        name = "name_course",
        nullable = false
    )
    private String name;

    /** Course description */
    @Column(
        name = "description_course",
        nullable = true,
        columnDefinition = "text"
    )
    private String description;

    /** Estimated duration of the course in minutes */
    @Column(
        name = "durationmin_course",
        nullable = false
    )
    private Integer durationInMinutes;

    /** Optional external URL (e.g. landing page, external content) */
    @Column(
        name = "externalurl_course",
        nullable = true
    )
    private String externalUrl;

    /** Administrator (creator) of the course */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "admin_course",
        nullable = false
    )
    private User admin;

    /** Chapters associated with the course */
    @OneToMany(
        mappedBy = "id.course",
        fetch = FetchType.LAZY
    )
    private List<Chapter> chapters;
    
}
