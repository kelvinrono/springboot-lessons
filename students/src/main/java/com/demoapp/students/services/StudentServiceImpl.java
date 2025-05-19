package com.demoapp.students.services;

import com.demoapp.students.models.Student;
import com.demoapp.students.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    @Override
    public List<Student> getAllStudents() {
        List<Student> allStudents = studentRepository.findAll();
        System.out.println("Found students: " + allStudents);
        return allStudents;
    }


    @Override
    public Student getStudent(int id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.get();
    }
}
