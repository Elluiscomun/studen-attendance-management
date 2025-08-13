package com.fighthub.attentance.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.fighthub.attentance.controller.CourseController;
import com.fighthub.attentance.model.Course;

public class CourseAssembler implements RepresentationModelAssembler<Course, EntityModel<Course>> {
    @Override
    public EntityModel<Course> toModel(Course course) {
        return EntityModel.of(course,
            linkTo(methodOn(CourseController.class).one(course.getId())).withSelfRel(),
            linkTo(methodOn(CourseController.class).all()).withRel("courses")
        );
    }


}
