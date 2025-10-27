package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewMaterialRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Material;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.repository.MaterialRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service class for managing Material entities.
 * Provides CRUD operations and business logic.
 *
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class MaterialService {

    /** Repository for Material entities */
    private final MaterialRepository materialRepository;

    /** 
     * Retrieves a paginated list of all materials.
     * 
     * @param page the page number
     * @param size the page size
     * @return a page of Material entities
     */
    public Page<Material> getAllMaterials(Integer page, Integer size) {
    return materialRepository.findAll(
        Pageable
            .ofSize(size)
            .withPage(page)
        );
    }

    /** 
     * Retrieves a material by its ID.
     * 
     * @param id the material ID
     * @return the Material entity
     * @throws EntityNotFoundException if the material is not found
     */
    public Material getMaterialById(Long id) {
        return materialRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Material not found with id " + id));
    }

    /** 
     * Creates a new material.
     * 
     * @param req the NewMaterialRequest DTO
     * @return the newly created Material
     */
    public Material createMaterial(NewMaterialRequest req) {
        Material entity = Material.builder()
            .name(req.getName())
            .description(req.getDescription())
            .type(req.getType())
            .url(req.getUrl())
            .build();

        return materialRepository.save(entity);
    }

    /** 
     * Updates an existing material.
     * 
     * @param id the material ID
     * @param req the NewMaterialRequest DTO with updated data
     * @return the updated Material entity
     * @throws EntityNotFoundException if the material is not found
     */
    public Material updateMaterial(Long id, NewMaterialRequest req) {
        Material existing = materialRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Material not found with id " + id));

        existing.setName(req.getName());
        existing.setDescription(req.getDescription());
        existing.setType(req.getType());
        existing.setUrl(req.getUrl());

        return materialRepository.save(existing);
    }

    /** 
     * Deletes a material by its ID.
     * 
     * @param id the material ID
     * @throws EntityNotFoundException if the material is not found
     */
    public void deleteMaterial(Long id) {
        if (!materialRepository.existsById(id))
            throw new EntityNotFoundException("Material not found with id " + id);

        materialRepository.deleteById(id);
    }

}
