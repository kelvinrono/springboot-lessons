package com.demoapp.students.controllers;

import com.demoapp.students.models.Student;
import com.demoapp.students.services.StudentService;
import com.demoapp.students.services.StudentServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {


    private final StudentService studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        System.out.println("Returning students: " + students);
        return students;
    }


    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id){
      return studentService.getStudent(id);
    }

}
