package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return null;
        }
        return student.get();
    }

    public void updateStudentById(Long id, Student updatedStudent) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student studentToUpdate = student.get();
            studentToUpdate.setId(id);

            if (updatedStudent.getName() != null) {
                studentToUpdate.setName(updatedStudent.getName());
            }
            if (updatedStudent.getHouseType() != null) {
                studentToUpdate.setHouseType(updatedStudent.getHouseType());
            }
            if (updatedStudent.getPetType() != null) {
                studentToUpdate.setPetType(updatedStudent.getPetType());
            }

            studentRepository.save(studentToUpdate);
        }
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }
}
