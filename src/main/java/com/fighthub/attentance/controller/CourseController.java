package com.fighthub.attentance.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.sql.results.graph.collection.internal.CollectionAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fighthub.attentance.assembler.CourseAssembler;
import com.fighthub.attentance.model.Course;
import com.fighthub.attentance.respository.CourseRepository;

@RestController
public class CourseController {
    private final CourseAssembler assembler;
    private final CourseRepository respository;
    private final String URL_BASE = "v1/courses/";

    public CourseController(CourseAssembler assembler, CourseRepository respository) {
        this.assembler = assembler;
        this.respository = respository;
    }

    @GetMapping(URL_BASE)
    public CollectionModel<EntityModel<Course>> all(){
        List<EntityModel<Course>> courseModels = respository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(courseModels);
    }
    
    @GetMapping(URL_BASE + "{id}")
    public ResponseEntity<EntityModel<Course>> one(@PathVariable UUID id) {
        Course course = respository.findById(id)
            .orElseThrow(() -> { throw new RuntimeException("Course not found"); });
        return ResponseEntity.ok(assembler.toModel(course));
    }

    @PostMapping(URL_BASE)
    public ResponseEntity<EntityModel<Course>> create(@RequestBody Course course) {
        EntityModel<Course> entityModel = assembler.toModel(respository.save(course));
        return ResponseEntity.created(entityModel.getRequiredLink("self").toUri())
            .body(entityModel);
    }

    @PostMapping(URL_BASE + "{id}")
    public ResponseEntity<EntityModel<Course>> update(@PathVariable UUID id, @RequestBody Course course) {
        Course updatedCourse = respository.findById(id)
            .map(existingCourse -> {
                existingCourse.setName(course.getName());
                existingCourse.setDescription(course.getDescription());
                return respository.save(existingCourse);
            })
            .orElseThrow(() -> { throw new RuntimeException("Course not found"); });
        return ResponseEntity.ok(assembler.toModel(updatedCourse));
    }

    @DeleteMapping(URL_BASE + "{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Course course = respository.findById(id)
            .orElseThrow(() -> { throw new RuntimeException("Course not found"); });
        course.disable();
        Course updatedCourse = respository.save(course);
        return ResponseEntity.ok(assembler.toModel(updatedCourse));
    }

}
