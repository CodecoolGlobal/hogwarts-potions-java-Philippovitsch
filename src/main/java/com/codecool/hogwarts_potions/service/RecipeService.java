package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Ingredient;
import com.codecool.hogwarts_potions.model.Recipe;
import com.codecool.hogwarts_potions.service.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeByIngredients(Set<Ingredient> ingredients) {
        for (Recipe recipe : getAllRecipes()) {
            Set<String> potionIngredients = recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toSet());
            Set<String> recipeIngredients = ingredients.stream().map(Ingredient::getName).collect(Collectors.toSet());
            if(potionIngredients.equals(recipeIngredients)) {
                return recipe;
            }
        }
        return null;
    }

    public List<Recipe> getRecipesById(Long studentId) {
        return getAllRecipes().stream()
                .filter(recipe -> recipe.getBrewer().getId().equals(studentId))
                .collect(Collectors.toList());
    }

    public void addRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }
}
