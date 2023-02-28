package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.model.dto.PotionDTO;
import com.codecool.hogwarts_potions.service.repositories.PotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PotionService {

    private final PotionRepository potionRepository;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final StudentService studentService;

    @Autowired
    public PotionService(PotionRepository potionRepository, IngredientService ingredientService,
                         RecipeService recipeService, StudentService studentService) {
        this.potionRepository = potionRepository;
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.studentService = studentService;
    }

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }

    public List<Potion> getPotionsById(Long studentId) {
        return potionRepository.findAll().stream()
                .filter(potion -> potion.getBrewer().getId().equals(studentId))
                .collect(Collectors.toList());
    }

    public void addPotion(Potion potion) {
        BrewingStatus brewingStatus = getBrewingStatus(potion);
        potion.setBrewingStatus(brewingStatus);
        potionRepository.save(potion);
    }

    public Potion brewPotion(PotionDTO potionDTO){
        Student potionBrewer = studentService.getStudentById(potionDTO.getStudentId());
        String potionName = String.format("%s's potion", potionBrewer.getName());
        Set<Ingredient> potionIngredients = getIngredients(potionDTO.getIngredients());
        Potion potion = Potion.builder().brewer(potionBrewer).name(potionName).ingredients(potionIngredients).build();

        BrewingStatus brewingStatus = getBrewingStatus(potion);
        potion.setBrewingStatus(brewingStatus);

        Recipe recipe = null;
        if (brewingStatus == BrewingStatus.DISCOVERY) {
            potionName = String.format(
                    "%s's discovery #%s"
                    , potionBrewer.getName()
                    , getPotionsById(potionDTO.getStudentId()).size()
            );
            recipe = Recipe.builder()
                    .name(potionName)
                    .brewer(potionBrewer)
                    .ingredients(potionIngredients)
                    .build();
            potion.setName(potionName);
            recipeService.addRecipe(recipe);
        } else if (brewingStatus == BrewingStatus.REPLICA) {
            recipe = recipeService.getRecipeByIngredients(potionIngredients);
        }

        potion.setRecipe(recipe);
        return potion;
    }

    private Set<Ingredient> getIngredients(List<String> ingredients) {
        return ingredients.stream()
                .map(ingredientService::getIngredientByName)
                .collect(Collectors.toSet());
    }

    private BrewingStatus getBrewingStatus(Potion potion) {
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
