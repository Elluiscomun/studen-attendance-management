package com.fighthub.attentance.model;

import java.util.UUID;

import org.hibernate.annotations.ManyToAny;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NonNull
    private String date;
    @NonNull
    private boolean present;
    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    private Boolean active = true;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public void disable() {
        this.active = false;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
