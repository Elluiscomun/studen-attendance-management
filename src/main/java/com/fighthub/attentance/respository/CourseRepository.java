package com.fighthub.attentance.respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fighthub.attentance.model.Course;

public interface CourseRepository extends JpaRepository<Course, UUID>{
    
}
