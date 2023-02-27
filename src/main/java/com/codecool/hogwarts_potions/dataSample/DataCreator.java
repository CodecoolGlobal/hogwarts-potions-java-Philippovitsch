package com.codecool.hogwarts_potions.dataSample;

import com.codecool.hogwarts_potions.model.HouseType;
import com.codecool.hogwarts_potions.model.PetType;
import com.codecool.hogwarts_potions.model.Room;
import com.codecool.hogwarts_potions.model.Student;
import com.codecool.hogwarts_potions.service.RoomService;
import com.codecool.hogwarts_potions.service.StudentService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DataCreator {

    RoomService roomService;
    StudentService studentService;

    public DataCreator(RoomService roomService, StudentService studentService) {
        this.roomService = roomService;
        this.studentService = studentService;
        init();
    }

    private void init() {
        Room room1 = Room.builder().name("Room1").capacity(2).residents(new HashSet<>()).build();
        Room room2 = Room.builder().name("Room2").capacity(2).residents(new HashSet<>()).build();
        Room room3 = Room.builder().name("Room3").capacity(2).residents(new HashSet<>()).build();
        Student student1 = Student.builder().name("Student1").houseType(HouseType.GRYFFINDOR).petType(PetType.OWL).build();
        Student student2 = Student.builder().name("Student2").houseType(HouseType.HUFFLEPUFF).petType(PetType.NONE).build();

        room1.setResidents(new HashSet<>(Collections.singletonList(student1)));
        room2.setResidents(new HashSet<>(Collections.singletonList(student2)));

        studentService.addStudent(student1);
        studentService.addStudent(student2);
        roomService.addRoom(room1);
        roomService.addRoom(room2);
        roomService.addRoom(room3);
    }
}
