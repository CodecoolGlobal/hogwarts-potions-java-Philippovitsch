package com.codecool.hogwarts_potions.service.repositories;

import com.codecool.hogwarts_potions.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
