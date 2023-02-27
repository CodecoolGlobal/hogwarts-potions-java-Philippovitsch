package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        //TODO
        return null;
    }

    public void addStudent(Student student) {
        //TODO
    }

    public Student getStudentById(Long id) {
        //TODO
        return null;
    }

    public void updateStudentById(Long id, Student updatedStudent) {
        //TODO
    }

    public void deleteStudentById(Long id) {
        //TODO
    }
}
