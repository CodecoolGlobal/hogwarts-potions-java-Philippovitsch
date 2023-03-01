package com.codecool.hogwarts_potions.controller;

import com.codecool.hogwarts_potions.model.Recipe;
import com.codecool.hogwarts_potions.model.dto.IngredientDTO;
import com.codecool.hogwarts_potions.model.dto.PotionDTO;
import com.codecool.hogwarts_potions.model.Potion;
import com.codecool.hogwarts_potions.service.PotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/potions")
public class PotionController {

    PotionService potionService;

    @Autowired
    public PotionController(PotionService potionService) {
        this.potionService = potionService;
    }

    @GetMapping
    public List<Potion> getAllPotions() {
        return potionService.getAllPotions();
    }

    @GetMapping("/{studentId}")
    public List<Potion> getPotionsById(@PathVariable("studentId") Long studentId) {
        return potionService.getPotionsByStudentId(studentId);
    }

    @PostMapping
    public Potion addPotion(@RequestBody PotionDTO potionDTO) {
        return potionService.addPotion(potionDTO);
    }

    @PostMapping("/brew/{studentId}")
    public Potion createNewPotion(@PathVariable("studentId") Long studentId) {
        return potionService.createNewPotion(studentId);
    }

    @PutMapping("/{potionId}/add")
    public Potion addIngredient(@PathVariable("potionId") Long potionId, @RequestBody IngredientDTO ingredientDTO) {
        return potionService.addIngredient(potionId, ingredientDTO);
    }

    @GetMapping("/{potionId}/help")
    public List<Recipe> getSimilarRecipes(@PathVariable("potionId") Long potionId) {
        return potionService.getSimilarRecipes(potionId);
    }

    @DeleteMapping("/{potionId}")
    public Potion deletePotion(@PathVariable("potionId") Long potionId) {
        return potionService.deletePotionById(potionId);
    }
}
