package com.fighthub.attentance.DTO;

import java.util.Date;
import java.util.UUID;

import com.fighthub.attentance.model.Course;

public class AttendanceRequest {
    private UUID student_id;
    private boolean present;
    private UUID course_id;

    // Getters and Setters
    public UUID getStudent_id() {
        return student_id;
    }

    public void setStudent_id(UUID student_id) {
        this.student_id = student_id;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public UUID getCourse_id() {
        return course_id;
    }

    public void setCourse_id(UUID course_id) {
        this.course_id = course_id;
    }
}
