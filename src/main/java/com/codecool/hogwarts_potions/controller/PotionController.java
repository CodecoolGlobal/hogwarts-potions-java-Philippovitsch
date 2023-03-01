package com.codecool.hogwarts_potions.controller;

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
        return potionService.getPotionsById(studentId);
    }

    @PostMapping
    public Potion addPotion(@RequestBody PotionDTO potionDTO) {
        return potionService.addPotion(potionDTO);
    }

    @PostMapping("/brew/{studentId}")
    public Potion createNewPotion(@PathVariable("studentId") Long studentId) {
        return potionService.createNewPotion(studentId);
    }
}
