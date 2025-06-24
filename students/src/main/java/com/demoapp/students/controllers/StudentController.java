package com.demoapp.students.controllers;

import com.demoapp.students.models.Student;
import com.demoapp.students.services.StudentService;
import com.demoapp.students.services.StudentServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id){
      return studentService.getStudent(id);
    }

    @PostMapping()
    public Student saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @PatchMapping("/{id}")
    Student updateStudent(@RequestBody Student student, @PathVariable int id){
        return studentService.updateStudent(student, id);
    }

    @DeleteMapping("/{id}")
    String deleteStudent(@PathVariable int id){
        return studentService.deleteStudent(id);
    }

}
