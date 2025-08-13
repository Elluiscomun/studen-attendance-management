package com.fighthub.attentance.service;

import com.fighthub.attentance.DTO.AttendanceRequest;
import com.fighthub.attentance.model.Attendance;
import com.fighthub.attentance.model.Course;
import com.fighthub.attentance.model.Student;

public class AttendanceService {
    public static Attendance createAttendance(AttendanceRequest request, Student student, Course course) {
        Attendance attendance = new Attendance();
        attendance.setDate(java.time.LocalDate.now().toString());
        attendance.setStudent(student);
        attendance.setPresent(request.isPresent());
        attendance.setCourse(course);
        return attendance;
    }
}
