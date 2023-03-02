package com.codecool.hogwarts_potions.dataSample;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.model.dto.IngredientDTO;
import com.codecool.hogwarts_potions.model.dto.PotionDTO;
import com.codecool.hogwarts_potions.service.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataCreator {

    private final RoomService roomService;
    private final StudentService studentService;
    private final IngredientService ingredientService;
    private final PotionService potionService;

    public DataCreator(RoomService roomService, StudentService studentService,
                       PotionService potionService, IngredientService ingredientService) {
        this.roomService = roomService;
        this.studentService = studentService;
        this.potionService = potionService;
        this.ingredientService = ingredientService;
        initRoomsAndPersons();
        initBrewing();
    }

    private void initRoomsAndPersons() {
        Room room1 = Room.builder().name("Gryffindor Shack").capacity(2).residents(new HashSet<>()).build();
        Room room2 = Room.builder().name("Hufflepuff Hut").capacity(2).residents(new HashSet<>()).build();
        Room room3 = Room.builder().name("Dumbledore Lodge").capacity(2).residents(new HashSet<>()).build();
        Student student1 = Student.builder().name("Harry Potter").houseType(HouseType.GRYFFINDOR).petType(PetType.OWL).build();
        Student student2 = Student.builder().name("Hermine Granger").houseType(HouseType.HUFFLEPUFF).petType(PetType.NONE).build();

        room1.setResidents(new HashSet<>(Collections.singletonList(student1)));
        room2.setResidents(new HashSet<>(Collections.singletonList(student2)));

        studentService.addStudent(student1);
        studentService.addStudent(student2);
        roomService.addRoom(room1);
        roomService.addRoom(room2);
        roomService.addRoom(room3);
    }

    private void initBrewing() {
        ingredientService.createNewIngredient("Avocado");
        ingredientService.createNewIngredient("Bone");
        ingredientService.createNewIngredient("DragonLiver");
        ingredientService.createNewIngredient("GingerRoot");
        ingredientService.createNewIngredient("HorseHair");
        ingredientService.createNewIngredient("SnakeWeed");
        ingredientService.createNewIngredient("UnicornHair");
        ingredientService.createNewIngredient("Frog");
        ingredientService.createNewIngredient("Liver");
        ingredientService.createNewIngredient("FireSeed");

        List<String> ingredientsList1 = Arrays.asList("Avocado", "Bone", "DragonLiver", "GingerRoot", "HorseHair");
        List<String> ingredientsList2 = Arrays.asList("Bone", "DragonLiver", "GingerRoot", "HorseHair", "SnakeWeed");
        List<String> ingredientsList3 = Arrays.asList("DragonLiver", "GingerRoot", "HorseHair", "SnakeWeed", "UnicornHair");
        List<String> ingredientsList4 = Arrays.asList("GingerRoot", "HorseHair", "SnakeWeed", "UnicornHair", "Frog");
        List<String> ingredientsList5 = Arrays.asList("HorseHair", "SnakeWeed", "UnicornHair", "Frog", "Liver");
        PotionDTO potionDTO1 = PotionDTO.builder().potionName("Baneberry Potion").studentId(1L).ingredients(ingredientsList1).build();
        PotionDTO potionDTO2 = PotionDTO.builder().potionName("Draught of Peace").studentId(1L).ingredients(ingredientsList2).build();
        PotionDTO potionDTO3 = PotionDTO.builder().potionName("Invisibility Potion").studentId(1L).ingredients(ingredientsList3).build();
        PotionDTO potionDTO4 = PotionDTO.builder().potionName("Sleeping Draught").studentId(2L).ingredients(ingredientsList4).build();
        PotionDTO potionDTO5 = PotionDTO.builder().potionName("Weedosoros").studentId(2L).ingredients(ingredientsList5).build();
        potionService.addPotion(potionDTO1);
        potionService.addPotion(potionDTO2);
        potionService.addPotion(potionDTO3);
        potionService.addPotion(potionDTO4);
        potionService.addPotion(potionDTO5);

        Long potionId1 = potionService.createNewPotion(1L).getId();
        potionService.addIngredient(potionId1, IngredientDTO.builder().ingredientName("Avocado").build());
        potionService.addIngredient(potionId1, IngredientDTO.builder().ingredientName("Bone").build());
        potionService.addIngredient(potionId1, IngredientDTO.builder().ingredientName("DragonLiver").build());

        Long potionId2 = potionService.createNewPotion(2L).getId();
        potionService.addIngredient(potionId2, IngredientDTO.builder().ingredientName("GingerRoot").build());
        potionService.addIngredient(potionId2, IngredientDTO.builder().ingredientName("HorseHair").build());
        potionService.addIngredient(potionId2, IngredientDTO.builder().ingredientName("SnakeWeed").build());
    }
}
