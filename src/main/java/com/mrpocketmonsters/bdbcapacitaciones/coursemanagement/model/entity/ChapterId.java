package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Composite primary key class for Chapter entity.
 * Consists of courseId and order fields.
 * 
 * @author Nicolás Sabogal
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ChapterId {

    /** Identifier of the course to which the chapter belongs */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "course_chapter",
        referencedColumnName = "id_course",
        nullable = false
    )
    private Course course;

    /** Chapter order within the course */
    @NotNull
    @Column(
        name = "order_chapter",
        nullable = false
    )
    private Integer order;
    
}
