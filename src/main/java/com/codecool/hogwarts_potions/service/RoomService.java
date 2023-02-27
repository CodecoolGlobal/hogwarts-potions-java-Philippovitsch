package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.PetType;
import com.codecool.hogwarts_potions.model.Room;
import com.codecool.hogwarts_potions.service.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return null;
        }
        return room.get();
    }

    public void updateRoomById(Long id, Room updatedRoom) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            Room roomToUpdate = room.get();
            roomToUpdate.setId(id);

            if (updatedRoom.getName() != null) {
                roomToUpdate.setName(updatedRoom.getName());
            }
            if (updatedRoom.getCapacity() != null) {
                roomToUpdate.setCapacity(updatedRoom.getCapacity());
            }
            if (updatedRoom.getResidents() != null) {
                roomToUpdate.setResidents(updatedRoom.getResidents());
            }

            roomRepository.save(roomToUpdate);
        }
    }

    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }

    public List<Room> getRoomsForRatOwners() {
        return roomRepository.findAll().stream()
                .filter(this::isRatSafe)
                .collect(Collectors.toList());
    }

    private boolean isRatSafe(Room room) {
        return room.getResidents().stream()
                .anyMatch(resident -> resident.getPetType() != PetType.CAT && resident.getPetType() != PetType.OWL);
    }
}
