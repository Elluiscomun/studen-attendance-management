package com.fighthub.attentance.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fighthub.attentance.assembler.StudentModelAssembler;
import com.fighthub.attentance.exception.StudentNotFoundException;
import com.fighthub.attentance.model.Student;
import com.fighthub.attentance.respository.StudentRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class StudentController {

    private final StudentModelAssembler assembler;
    private final StudentRepository repository;
    private final String URL_BASE = "v1/students/";

    StudentController(StudentModelAssembler assembler, StudentRepository repository) {
        this.assembler = assembler;
        this.repository = repository;
    }

    @GetMapping(URL_BASE)
    public CollectionModel<EntityModel<Student>> getAllStudents() {
        List<EntityModel<Student>> studentModels = repository.findAll().stream()
            .map(assembler::toModel)
            .toList();
        return CollectionModel.of(studentModels,
                linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel());
    }
    
    @PostMapping(URL_BASE)
    ResponseEntity<?> createStudent(@RequestBody Student newStudent){
        EntityModel<Student> entityModel = assembler.toModel(repository.save(newStudent));
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping(URL_BASE + "{id}")
    ResponseEntity<?> getStudentById(@PathVariable UUID id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException());
        EntityModel<Student> entityModel = assembler.toModel(student);
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping(URL_BASE + "{id}")
    ResponseEntity<?> updateStudentById(@PathVariable UUID id, @RequestBody Student updatedStudent) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException());
        student.update(updatedStudent);
        Student savedStudent = repository.save(student);
        EntityModel<Student> entityModel = assembler.toModel(savedStudent);
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping(URL_BASE + "identification/{identificationNumber}")
    public
    ResponseEntity<?> getStudentByIdentificationNumber(@PathVariable String identificationNumber) {
        Student student = repository.findByIdentificationNumber(identificationNumber)
            .orElseThrow(() -> new StudentNotFoundException());
        EntityModel<Student> entityModel = assembler.toModel(student);
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping(URL_BASE + "identification/{identificationNumber}")
    public ResponseEntity<?> updateStudentByIdentificationNumber(
            @PathVariable String identificationNumber, @RequestBody Student updatedStudent) {
        Student student = repository.findByIdentificationNumber(identificationNumber)
            .orElseThrow(() -> new StudentNotFoundException());
        student.update(updatedStudent);
        Student savedStudent = repository.save(student);
        EntityModel<Student> entityModel = assembler.toModel(savedStudent);
        return ResponseEntity.ok(entityModel);
    }


    @GetMapping(URL_BASE + "phone/{phone}")
    public CollectionModel<EntityModel<Student>> getStudentsByPhone(@PathVariable String phone) {
        List<EntityModel<Student>> studentModels = repository.findByPhone(phone).stream()
            .map(assembler::toModel)
            .toList();
        return CollectionModel.of(studentModels,
                linkTo(methodOn(StudentController.class).getStudentsByPhone(phone)).withSelfRel());
    }

    @DeleteMapping(URL_BASE+"{id}")
    public EntityModel<Student> deleteStudent(@PathVariable UUID id) {
        Student student = repository.findById(id)
            .map(employee -> {
                employee.disableStudent();
                return repository.save(employee);
            })
            .orElseGet(() -> {
                throw new StudentNotFoundException();
            });
        return assembler.toModel(student);
    }

}
