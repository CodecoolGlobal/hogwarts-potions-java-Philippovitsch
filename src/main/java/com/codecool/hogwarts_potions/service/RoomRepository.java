package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
