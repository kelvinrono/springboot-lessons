package com.demoapp.students.services;

import com.demoapp.students.models.Student;
import com.demoapp.students.repositories.StudentRepository;
import com.demoapp.students.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public ApiResponse<Page<Student>> getAllStudents(int pageNumber, int pageSize) {
        try {

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
           // Pageable pageable1 = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());

            Page<Student> allStudents = studentRepository.findAll(pageable);

            return new ApiResponse<>(true, "All students fetched successfully", allStudents);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ApiResponse<>(false, "Oops! Something went wrong with the server", null);
        }
    }


    @Override
    public ApiResponse<Student> getStudent(int id) {
        try {

            Optional<Student> student = studentRepository.findById(id); //null

            if(student.isEmpty()){
                return new ApiResponse<>(false, "student with the given id does not exist", null);
            }

            return new ApiResponse<>(true, "Student data retrieved successfully", student.get());
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ApiResponse<>(false, "Oops! Something went wrong with the server ", null);
        }

    }

    @Override
    public ApiResponse<Void> saveStudent(Student student) {

        try {
            Optional<Student> existingStudent = studentRepository.findByEmail(student.getEmail());

            if(existingStudent.isPresent()) {
                return new ApiResponse<>(false, "User with that email already exist", null);
            }

            studentRepository.save(student);

            return new ApiResponse<>(true, "Student saved successfully", null);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ApiResponse<>(false, "Oops! Something went wrong with the server", null);
        }

    }

    @Override
    public ApiResponse<Void> updateStudent(Student student, int id) {

        try {
            Optional<Student> optionalStudent = studentRepository.findById(id);

            if(optionalStudent.isEmpty()){
                return new ApiResponse<>(false, "Student with the given ID does not exist", null);
            }

            Student existingStudent = optionalStudent.get();

            existingStudent.setEmail(student.getEmail());
            existingStudent.setFirstName(student.getFirstName());

            studentRepository.save(existingStudent);

            return new ApiResponse<>(true, "User updated successfully", null);
        }
        catch ( Exception e){
            e.printStackTrace();
            return new ApiResponse<>(false, "Oops! Something went wrong with the server", null);
        }

    }

    @Override
    public ApiResponse<Void> deleteStudent(int id) {

        try{
            Optional<Student> existingStudent = studentRepository.findById(id);

            if(existingStudent.isEmpty()){
                return  new ApiResponse<>(false, "Student with the given ID does not exist", null);
            }

            existingStudent.get().setDeleted(true);

            studentRepository.save(existingStudent.get());
            return new ApiResponse<>(true, "User has been deleted successfully", null);

        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ApiResponse<>(false, "Oops! Something went wrong with the server", null);
        }

    }

}
