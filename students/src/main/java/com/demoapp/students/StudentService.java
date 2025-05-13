package com.demoapp.students;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private List<Student> students = new ArrayList<>();


    public StudentService(){

    }


    public List<Student> getAllStudents(){
        return students;
    }

    public Student getStudentById(int id){

        for(Student student: students){
            if(student.getId()==id){
                return student;
            }
        }
        return null;
    }


}
