package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Module;

/**
 * Repository interface for Module entities.
 * Extends JpaRepository to provide CRUD operations and query methods.
 * 
 * @author Nicolás Sabogal
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {
}
