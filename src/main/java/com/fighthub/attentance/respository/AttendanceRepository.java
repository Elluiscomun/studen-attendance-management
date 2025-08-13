package com.fighthub.attentance.respository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fighthub.attentance.model.Attendance;
import com.fighthub.attentance.model.Student;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    Optional<Attendance> findByStudent(Student student);
    Optional<Attendance> findByStudent_Phone(String phoneNumber);

}
