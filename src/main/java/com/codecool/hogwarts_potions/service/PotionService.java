package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.model.dto.IngredientDTO;
import com.codecool.hogwarts_potions.model.dto.PotionDTO;
import com.codecool.hogwarts_potions.service.constants.BrewingServiceConstants;
import com.codecool.hogwarts_potions.service.repositories.PotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public List<Potion> getPotionsByStudentId(Long studentId) {
        return potionRepository.findAll().stream()
                .filter(potion -> potion.getBrewer().getId().equals(studentId))
                .collect(Collectors.toList());
    }

    public Potion addPotion(PotionDTO potionDTO){
        Student potionBrewer = studentService.getStudentById(potionDTO.getStudentId());
        String potionName = String.format("%s's potion", potionBrewer.getName());
        Set<Ingredient> potionIngredients = getIngredients(potionDTO.getIngredients());
        Potion potion = Potion.builder().brewer(potionBrewer).name(potionName).ingredients(potionIngredients).build();

        BrewingStatus brewingStatus = getBrewingStatus(potion);
        potion.setBrewingStatus(brewingStatus);

        Recipe recipe = null;
        if (brewingStatus == BrewingStatus.DISCOVERY) {
            String recipeName = potionDTO.getPotionName();
            recipe = Recipe.builder()
                    .name(recipeName)
                    .brewer(potionBrewer)
                    .ingredients(potionIngredients)
                    .build();
            recipeService.addRecipe(recipe);
        } else if (brewingStatus == BrewingStatus.REPLICA) {
            recipe = recipeService.getRecipeByIngredients(potionIngredients);
        }

        potion.setRecipe(recipe);
        return potion;
    }

    public Potion createNewPotion(Long studentId) {
        Student student = studentService.getStudentById(studentId);
        BrewingStatus brewingStatus = BrewingStatus.BREW;
        String potionName = String.format("%s's potion", student.getName());
        Potion potion = Potion.builder().name(potionName).brewer(student).brewingStatus(brewingStatus).build();
        potionRepository.save(potion);
        return potion;
    }

    public Potion addIngredient(Long potionId, IngredientDTO ingredientDTO) {
        Potion potion = getPotionByPotionId(potionId);
        Ingredient newIngredient = getNewIngredient(ingredientDTO.getIngredientName());
        Set<Ingredient> existingIngredients = potion.getIngredients();

        existingIngredients.add(newIngredient);
        potion.setIngredients(existingIngredients);
        BrewingStatus brewingStatus = getBrewingStatus(potion);
        potion.setBrewingStatus(brewingStatus);

        Recipe recipe = null;
        if (brewingStatus == BrewingStatus.REPLICA) {
            recipe = recipeService.getRecipeByIngredients(existingIngredients);
        }
        potion.setRecipe(recipe);

        potionRepository.save(potion);

        return potion;
    }

    public List<Recipe> getSimilarRecipes(Long potionId) {
        Potion potion = getPotionByPotionId(potionId);
        Set<Ingredient> potionIngredients = potion.getIngredients();
        return compareIngredients(potionIngredients);
    }

    public Potion deletePotionById(Long potionId) {
        Potion potion = getPotionByPotionId(potionId);
        potionRepository.deleteById(potionId);
        return potion;
    }

    private List<Recipe> compareIngredients(Set<Ingredient> ingredients) {
        List<Recipe> similarRecipes = new ArrayList<>();
        for (Recipe recipe : recipeService.getAllRecipes()) {
            Set<String> recipeIngredients = recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toSet());
            Set<String> potionIngredients = ingredients.stream().map(Ingredient::getName).collect(Collectors.toSet());
            if (recipeIngredients.containsAll(potionIngredients)) {
                similarRecipes.add(recipe);
            }
        }
        return similarRecipes;
    }

    private Set<Ingredient> getIngredients(List<String> ingredients) {
        return ingredients.stream()
                .map(ingredientService::getIngredientByName)
                .collect(Collectors.toSet());
    }

    private BrewingStatus getBrewingStatus(Potion potion) {
        if (potion.getIngredients().size() < BrewingServiceConstants.MAX_INGREDIENTS_FOR_POTIONS) {
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

    private Potion getPotionByPotionId(Long potionId) {
        Optional<Potion> potion = potionRepository.findById(potionId);
        if (potion.isEmpty()) {
            return null;
        }
        return potion.get();
    }

    private Ingredient getNewIngredient(String ingredientName) {
        if (ingredientService.isExisting(ingredientName)) {
            return ingredientService.getIngredientByName(ingredientName);
        }
        return ingredientService.createNewIngredient(ingredientName);
    }
}
