package com.demoapp.students.services;

import com.demoapp.students.models.Student;
import com.demoapp.students.responses.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

   ApiResponse<Page<Student>> getAllStudents(int pageNumber, int pageSize);
   ApiResponse<Student> getStudent(int id);
   ApiResponse<Void> saveStudent(Student student);
   ApiResponse<Void> updateStudent(Student student, int id);
    ApiResponse<Void> deleteStudent(int id);
}
