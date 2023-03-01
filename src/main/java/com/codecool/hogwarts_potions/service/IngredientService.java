package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Ingredient;
import com.codecool.hogwarts_potions.service.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findByName(name);
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public Ingredient createNewIngredient(String ingredientName) {
        Ingredient ingredient = Ingredient.builder().name(ingredientName).build();
        addIngredient(ingredient);
        return ingredient;
    }

    public boolean isExisting(String ingredientName) {
        return ingredientRepository.existsByName(ingredientName);
    }
}
