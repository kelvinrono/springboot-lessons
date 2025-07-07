package com.demoapp.students.controllers;

import com.demoapp.students.models.Student;
import com.demoapp.students.responses.ApiResponse;
import com.demoapp.students.services.StudentService;
import com.demoapp.students.services.StudentServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ApiResponse<Page<Student>> getAllStudents(@RequestParam int pageNumber, @RequestParam int pageSize){
       return studentService.getAllStudents(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(@PathVariable int id){
      return studentService.getStudent(id);
    }

    @PostMapping()
    public ApiResponse<Void> saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @PatchMapping("/{id}")
   ApiResponse<Void> updateStudent(@RequestBody Student student, @PathVariable int id){
        return studentService.updateStudent(student, id);
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void>  deleteStudent(@PathVariable int id){
        return studentService.deleteStudent(id);
    }

}
