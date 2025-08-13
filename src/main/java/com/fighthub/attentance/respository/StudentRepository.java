package com.fighthub.attentance.respository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fighthub.attentance.model.Student;

public interface StudentRepository extends JpaRepository<Student, UUID>{
    Optional<Student> findByIdentificationNumber(String identificationNumber);
    List<Student> findByPhone(String phone);
}
