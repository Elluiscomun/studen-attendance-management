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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fighthub.attentance.DTO.AttendanceRequest;
import com.fighthub.attentance.assembler.AttendanceModelAssembler;
import com.fighthub.attentance.model.Attendance;
import com.fighthub.attentance.model.Course;
import com.fighthub.attentance.model.Student;
import com.fighthub.attentance.respository.AttendanceRepository;
import com.fighthub.attentance.respository.CourseRepository;
import com.fighthub.attentance.respository.StudentRepository;
import com.fighthub.attentance.service.AttendanceService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class AttendanceController {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceModelAssembler assembler;
    private final String URL_BASE = "v1/students/";

    public AttendanceController(AttendanceRepository attendanceRepository, 
        StudentRepository studentRepository, CourseRepository courseRepository, 
        AttendanceModelAssembler assembler) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.assembler = assembler;
    }

    @GetMapping(URL_BASE + "/attendances")
    public CollectionModel<EntityModel<Attendance>> getAllAttendances() {
        List<EntityModel<Attendance>> attendanceModels = attendanceRepository.findAll().stream()
            .map(assembler::toModel)
            .toList();
        return CollectionModel.of(attendanceModels,
                linkTo(methodOn(AttendanceController.class).getAllAttendances()).withSelfRel());
    }

    @GetMapping(URL_BASE + "/attendances/{id}")
    public EntityModel<Attendance> getAttendanceById(@PathVariable UUID id) {
        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Attendance not found for ID: " + id));
        return assembler.toModel(attendance);
    }

    @GetMapping(URL_BASE+"/phone/{phoneNumber}/attendances")
    public EntityModel<Attendance> getAttendanceByPhoneNumber(@PathVariable String phoneNumber) {
        Attendance attendance = attendanceRepository.findByStudent_Phone(phoneNumber)
            .orElseThrow(() -> new RuntimeException("Attendance not found for phone number: " + phoneNumber));
        return assembler.toModel(attendance);
    }

    @PostMapping(URL_BASE + "/attendances")
    public ResponseEntity<?> createAttendance(@RequestBody AttendanceRequest request) {
        Student student = studentRepository.findById(request.getStudent_id())
            .orElseThrow(() -> new RuntimeException("Student not found for ID : " + request.getStudent_id()));
        Course course = courseRepository.findById(request.getCourse_id())
            .orElseThrow(() -> new RuntimeException("Course not found for ID : " + request.getCourse_id()));    
        Attendance attendance = AttendanceService.createAttendance(request, student, course);
        Attendance savedAttendance = attendanceRepository.save(attendance);

        EntityModel<Attendance> entityModel = assembler.toModel(savedAttendance);
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping(URL_BASE + "/attendances/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable UUID id) {
        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Attendance not found for ID: " + id));
        attendance.disable();
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return ResponseEntity.ok(assembler.toModel(savedAttendance));
    }
}