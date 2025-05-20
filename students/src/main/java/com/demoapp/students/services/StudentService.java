package com.demoapp.students.services;

import com.demoapp.students.models.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();
    Student getStudent(int id);
    Student saveStudent(Student student);
    Student updateStudent(Student student, int id);
}
