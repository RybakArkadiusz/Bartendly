package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Cocktail;
import com.example.Bartendly.model.NonAlcoholicIngredient;
import com.example.Bartendly.model.db.CocktailNonAlcoholicIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailNonAlcoholicIngredientRepository extends JpaRepository {
    List<CocktailNonAlcoholicIngredient> findByNonAlcoholicIngredient(NonAlcoholicIngredient nonAlcoholicIngredient);
    List<CocktailNonAlcoholicIngredient> findByCocktail(Cocktail cocktail);
}
