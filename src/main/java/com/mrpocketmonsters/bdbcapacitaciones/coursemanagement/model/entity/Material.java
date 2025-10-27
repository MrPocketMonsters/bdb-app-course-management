package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a material in the course management system.
 * Maps to the "material" table in the database.
 * 
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "material")
public class Material {

    /** Unique identifier for the material */
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "seq_material"
    )
    @SequenceGenerator(
        name = "seq_material",
        sequenceName = "seq_material",
        allocationSize = 1
    )
    @NotNull
    @Column(
        name = "id_material",
        nullable = false
    )
    private Long id;

    /** Name of the material */
    @NotEmpty
    @Column(
        name = "name_material",
        nullable = false
    )
    private String name;

    /** Description of the material */
    @Column(
        name = "description_material",
        nullable = true
    )
    private String description;

    /** Type of the material */
    @NotNull
    @Column(
        name = "type_material",
        nullable = false
    )
    private MaterialType type;

    /** URL of the material */
    @NotEmpty
    @Column(
        name = "url_material",
        nullable = false
    )
    private String url;
    
}
