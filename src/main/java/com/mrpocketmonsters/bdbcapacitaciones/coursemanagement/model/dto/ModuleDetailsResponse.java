package com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.dto;

import java.util.List;

import com.mrpocketmonsters.bdbcapacitaciones.coursemanagement.model.entity.Module;

import lombok.Data;

/**
 * Data Transfer Object for detailed information about a Module.
 * 
 * @author Nicolás Sabogal
 */
@Data
public class ModuleDetailsResponse {

    /** Unique identifier for the module */
    private Long id;

    /** Name of the module */
    private String name;

    /** Description of the module */
    private String description;

    /** Courses associated with the module */
    private List<CourseListElement> courses;

    /** Creator of the module */
    private String createdBy;

    /**
     * Maps a Module entity to a ModuleDetailsResponse DTO.
     * 
     * @param module the Module entity to map
     * @return the corresponding ModuleDetailsResponse DTO
     */
    public static ModuleDetailsResponse of(Module module) {
        ModuleDetailsResponse resp = new ModuleDetailsResponse();
        resp.setId(module.getId());
        resp.setName(module.getName());
        resp.setDescription(module.getDescription());
        resp.setCourses(
            module.getCourses().stream()
                .map(CourseListElement::of)
                .toList()
        );
        resp.setCreatedBy(module.getAdmin().getName());
        return resp;
    }
    
}
