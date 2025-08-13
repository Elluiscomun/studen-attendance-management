package com.fighthub.attentance.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.fighthub.attentance.controller.AttendanceController;
import com.fighthub.attentance.model.Attendance;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AttendanceModelAssembler implements RepresentationModelAssembler<Attendance, EntityModel<Attendance>> {
    @Override
    public EntityModel<Attendance> toModel(Attendance attendance) {
        return EntityModel.of(attendance,
                linkTo(methodOn(AttendanceController.class).getAttendanceById(attendance.getId())).withSelfRel(),
                linkTo(methodOn(AttendanceController.class).getAllAttendances()).withRel("attendances"));
    }
}
