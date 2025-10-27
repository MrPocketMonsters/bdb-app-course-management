package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.MaterialDetailsResponse;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.MaterialListElement;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.NewMaterialRequest;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto.MaterialIdentifierDto;
import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.service.MaterialService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST controller for managing Material entities.
 * Provides endpoints for CRUD operations.
 *
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/materials")
public class MaterialController {

    /** Service for Material operations */
    private final MaterialService materialService;

    /**
     * Retrieves a paginated list of all materials.
     * 
     * @param page the page number
     * @param size the page size
     * @return a page of MaterialListElement DTOs
     */
    @GetMapping("/")
    public ResponseEntity<Page<MaterialListElement>> getAllMaterials(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(materialService.getAllMaterials(page, size));
    }

    /**
     * Retrieves a material by its ID.
     * 
     * @param id the material ID
     * @return the MaterialDetailsResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaterialDetailsResponse> getMaterialById(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.getMaterialById(id));
    }

    /**
     * Creates a new material.
     * 
     * @param material the NewMaterialRequest DTO
     * @return the MaterialIdentifierDto of the created material
     */
    @PostMapping("/")
    public ResponseEntity<MaterialIdentifierDto> newMaterial(@RequestBody NewMaterialRequest material) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materialService.createMaterial(material));
    }

    /**
     * Updates an existing material.
     * 
     * @param id the material ID
     * @param material the NewMaterialRequest DTO with updated data
     * @return the updated MaterialDetailsResponse DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDetailsResponse> updateMaterial(@PathVariable Long id, @RequestBody NewMaterialRequest material) {
        return ResponseEntity.ok(materialService.updateMaterial(id, material));
    }

    /**
     * Deletes a material by its ID.
     * 
     * @param id the material ID
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.ok().build();
    }

}
