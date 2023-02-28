package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.service.repositories.PotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PotionService {

    private final PotionRepository potionRepository;
    private final RecipeService recipeService;

    @Autowired
    public PotionService(PotionRepository potionRepository, RecipeService recipeService) {
        this.potionRepository = potionRepository;
        this.recipeService = recipeService;
    }

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }

    public void addPotion(Potion potion) {
        BrewingStatus brewingStatus = getBrewingStatus(potion);
        potion.setBrewingStatus(brewingStatus);
        potionRepository.save(potion);
    }

    public BrewingStatus getBrewingStatus(Potion potion) {
        if (potion.getIngredients().size() < 5) {
            return BrewingStatus.BREW;
        } else if (isKnownRecipe(potion.getIngredients())) {
            return BrewingStatus.REPLICA;
        } else {
            return BrewingStatus.DISCOVERY;
        }
    }

    private boolean isKnownRecipe(Set<Ingredient> ingredients) {
        for (Recipe recipe : recipeService.getAllRecipes()) {
            Set<String> potionIngredients = recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toSet());
            Set<String> recipeIngredients = ingredients.stream().map(Ingredient::getName).collect(Collectors.toSet());
            if(potionIngredients.equals(recipeIngredients)) {
                return true;
            }
        }
        return false;
    }

}
