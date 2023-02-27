package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        //TODO
        return null;
    }

    public void addRoom(Room room) {
        //TODO
    }

    public Room getRoomById(Long id) {
        //TODO
        return null;
    }

    public void updateRoomById(Long id, Room updatedRoom) {
        //TODO
    }

    public void deleteRoomById(Long id) {
        //TODO
    }

    public List<Room> getRoomsForRatOwners() {
        //TODO
        return null;
    }
}
