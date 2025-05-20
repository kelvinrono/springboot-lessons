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
        try {
            List<Student> allStudents = studentRepository.findAll();
            System.out.println("Found students: " + allStudents);
            return allStudents;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Student getStudent(int id) {
        try {
            Optional<Student> student = studentRepository.findById(id);
            if(student.isEmpty()){
                throw new RuntimeException("student with the given id does not exist");
            }
            return student.get();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }

    }

    @Override
    public Student saveStudent(Student student) {

        try {
            Optional<Student> existingStudent = studentRepository.findByEmail(student.getEmail());

            if(existingStudent.isPresent()) {
                throw  new RuntimeException("Student with the given email already exist");
            }

            studentRepository.save(student);

            return student;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Student updateStudent(Student student, int id) {

        try {
            Optional<Student> optionalStudent = studentRepository.findById(id);

            if(optionalStudent.isEmpty()){
                throw new RuntimeException("Student with the given ID does not exist");
            }

            Student existingStudent = optionalStudent.get();

            existingStudent.setEmail(student.getEmail());
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setCourse(student.getCourse());
            existingStudent.setRegNumber(student.getRegNumber());

            studentRepository.save(existingStudent);

            return existingStudent;
        }
        catch ( Exception e){
            e.printStackTrace();
            return null;
        }

    }


}
