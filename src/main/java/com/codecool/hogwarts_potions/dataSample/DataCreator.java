package com.codecool.hogwarts_potions.dataSample;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.service.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataCreator {

    private final RoomService roomService;
    private final StudentService studentService;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final PotionService potionService;

    private Student student1;
    private Student student2;

    public DataCreator(RoomService roomService, StudentService studentService, PotionService potionService,
                       IngredientService ingredientService, RecipeService recipeService) {
        this.roomService = roomService;
        this.studentService = studentService;
        this.potionService = potionService;
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        initRoomsAndPersons();
        initBrewing();
    }

    private void initRoomsAndPersons() {
        Room room1 = Room.builder().name("Room1").capacity(2).residents(new HashSet<>()).build();
        Room room2 = Room.builder().name("Room2").capacity(2).residents(new HashSet<>()).build();
        Room room3 = Room.builder().name("Room3").capacity(2).residents(new HashSet<>()).build();
        student1 = Student.builder().name("Student1").houseType(HouseType.GRYFFINDOR).petType(PetType.OWL).build();
        student2 = Student.builder().name("Student2").houseType(HouseType.HUFFLEPUFF).petType(PetType.NONE).build();

        room1.setResidents(new HashSet<>(Collections.singletonList(student1)));
        room2.setResidents(new HashSet<>(Collections.singletonList(student2)));

        studentService.addStudent(student1);
        studentService.addStudent(student2);
        roomService.addRoom(room1);
        roomService.addRoom(room2);
        roomService.addRoom(room3);
    }

    private void initBrewing() {
        Ingredient ingredient1 = Ingredient.builder().name("Avocado").build();
        Ingredient ingredient2 = Ingredient.builder().name("Bone").build();
        Ingredient ingredient3 = Ingredient.builder().name("DragonLiver").build();
        Ingredient ingredient4 = Ingredient.builder().name("GingerRoot").build();
        Ingredient ingredient5 = Ingredient.builder().name("HorseHair").build();
        Ingredient ingredient6 = Ingredient.builder().name("SnakeWeed").build();
        Ingredient ingredient7 = Ingredient.builder().name("UnicornHair").build();

        Set<Ingredient> ingredientsList1 = new HashSet<>(Arrays.asList(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5));
        Set<Ingredient> ingredientsList2 = new HashSet<>(Arrays.asList(ingredient2, ingredient3, ingredient4, ingredient5, ingredient6));

        Recipe recipe1 = Recipe.builder().name("Student1's discovery #1").brewer(student1).ingredients(ingredientsList1).build();
        Recipe recipe2 = Recipe.builder().name("Student2's discovery #1").brewer(student2).ingredients(ingredientsList2).build();

        ingredientService.addIngredient(ingredient1);
        ingredientService.addIngredient(ingredient2);
        ingredientService.addIngredient(ingredient3);
        ingredientService.addIngredient(ingredient4);
        ingredientService.addIngredient(ingredient5);
        ingredientService.addIngredient(ingredient6);
        ingredientService.addIngredient(ingredient7);
        recipeService.addRecipe(recipe1);
        recipeService.addRecipe(recipe2);

        potionService.createNewPotion(1L);
        potionService.createNewPotion(2L);
    }
}
