package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a chapter (capítulo) in the course management system.
 * Uses a composite primary key defined by {@link ChapterId} via @EmbeddedId.
 *
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chapter")
public class Chapter {

    /** Composite identifier for the chapter (embedded) */
    @NotNull
    @EmbeddedId
    private ChapterId id;

    /** Chapter title */
    @NotEmpty
    @Column(
        name = "name_chapter",
        nullable = false
    )
    private String name;

    /** Short description of the chapter */
    @Column(
        name = "description_chapter",
        nullable = true,
        columnDefinition = "text"
    )
    private String description;

    /** Full content of the chapter (markdown/html/plaintext) */
    @Column(
        name = "content_chapter",
        nullable = true,
        columnDefinition = "text"
    )
    private String content;
    
    /** Administrator (creator) of the chapter */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "admin_chapter",
        nullable = false
    )
    private User admin;

}
