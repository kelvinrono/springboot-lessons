package com.demoapp.students.services;

import com.demoapp.students.DTOs.AuthDTO;
import com.demoapp.students.models.Department;
import com.demoapp.students.models.Passport;
import com.demoapp.students.models.Student;
import com.demoapp.students.repositories.DepartmentRepository;
import com.demoapp.students.repositories.StudentRepository;
import com.demoapp.students.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder){
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse<List<Student>> getAllStudents() {
        try {

            List<Student> allStudents = studentRepository.findAll();

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

            Optional<Department> existingDepartment = departmentRepository.findById(student.getDepartment().getId());

            if(existingDepartment.isEmpty()) {
                return new ApiResponse<>(false, "Department with the given id does not exist", null);
            }

            if(existingStudent.isPresent()) {
                return new ApiResponse<>(false, "User with that email already exist", null);
            }

            String encodedPassword = passwordEncoder.encode(student.getPassword());
            student.setPassword(encodedPassword);

            Passport passport = new Passport();
            passport.setPassportNumber(student.getPassport().getPassportNumber());

            student.setPassport(passport);
            student.setDepartment(existingDepartment.get());

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

    @Override
    public ApiResponse<Void> loginUser(AuthDTO authDTO) {
        try {

            Optional<Student> existingStudent = studentRepository.findByEmail(authDTO.getEmail());

            if(existingStudent.isEmpty()) {
                return new ApiResponse<>(false, "Invalid credentials", null);
            }

            boolean validPassword = passwordEncoder.matches(authDTO.getPassword(), existingStudent.get().getPassword());

            if (!validPassword){
                return new ApiResponse<>(false, "Invalid credentials", null);
            }

            return new ApiResponse<>(true, "Login Successful", null);


        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ApiResponse<>(false, "Oops! Something went wrong with the server", null);
        }
    }

}
