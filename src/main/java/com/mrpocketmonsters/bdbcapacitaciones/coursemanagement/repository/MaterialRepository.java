package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Material;

/**
 * Repository interface for Material entities.
 * Handles CRUD operations and data access.
 *
 * @author Nicolás Sabogal
 */
public interface MaterialRepository extends JpaRepository<Material, Long> {

}
