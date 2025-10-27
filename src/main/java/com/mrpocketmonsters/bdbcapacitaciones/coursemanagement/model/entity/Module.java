package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
 * Entity representing a module in the course management system.
 * Maps to the "module" table in the database.
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
    name = "module",
    uniqueConstraints = @UniqueConstraint(columnNames = "name_module")
)
public class Module {

    /** Unique identifier for the module */
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "seq_module"
    )
    @SequenceGenerator(
        name = "seq_module",
        sequenceName = "seq_module",
        allocationSize = 1
    )
    @NotNull
    @Column(
        name = "id_module",
        nullable = false
    )
    private Long id;

    /** Name of the module */
    @NotEmpty
    @Column(
        name = "name_module",
        nullable = false
    )
    private String name;

    /** Description of the module */
    @NotEmpty
    @Column(
        name = "description_module",
        nullable = true
    )
    private String description;

    /** Administrator (creator) of the module */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "admin_module",
        nullable = false
    )
    private User admin;

    /** Courses associated with the module */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "modulecourse",
        joinColumns = @JoinColumn(name = "module_modulecourse"),
        inverseJoinColumns = @JoinColumn(name = "course_modulecourse")
    )
    private Set<Course> courses;
    
}
