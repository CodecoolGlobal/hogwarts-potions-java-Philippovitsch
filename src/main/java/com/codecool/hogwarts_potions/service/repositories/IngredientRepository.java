package com.codecool.hogwarts_potions.service.repositories;

import com.codecool.hogwarts_potions.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
