package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
 * Entity representing a user's history (registro de consumo de un capítulo).
 *
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userhistory")
public class UserHistory {

    /** Unique identifier for the history record */
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "seq_userhistory"
    )
    @SequenceGenerator(
        name = "seq_userhistory",
        sequenceName = "seq_userhistory",
        allocationSize = 1
    )
    @NotNull
    @Column(
        name = "id_userhistory",
        nullable = false
    )
    private Long id;

    /** User associated with this history record */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_userhistory",
        nullable = false
    )
    private User user;

    /** Chapter associated with this history record */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(
            name = "course_userhistory",
            referencedColumnName = "course_chapter",
            nullable = false
        ),
        @JoinColumn(
            name = "order_userhistory",
            referencedColumnName = "order_chapter",
            nullable = false
        )
    })
    private Chapter chapter;

    /** Start timestamp of the interaction */
    @NotNull
    @Column(
        name = "starttime_userhistory",
        nullable = false
    )
    private Instant start;

    /** End timestamp of the interaction (nullable until finished) */
    @Column(
        name = "endtime_userhistory",
        nullable = true
    )
    private Instant end;
    
}
