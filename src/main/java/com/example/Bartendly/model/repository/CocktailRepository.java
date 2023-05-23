package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Cocktail;
import com.example.Bartendly.model.FlavourProfile;
import com.example.Bartendly.model.PreparationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Query(value = "SELECT c FROM Cocktail c WHERE " +
            ":numberOfAlcohols = 0 OR EXISTS (SELECT 1 FROM CocktailAlcohol ca WHERE ca.cocktail.id = c.id AND ca.alcohol.id IN :alcoholIds GROUP BY ca.cocktail.id HAVING COUNT(ca.cocktail.id) = :numberOfAlcohols) " +
            "AND :numberOfFlavours = 0 OR EXISTS (SELECT 1 FROM CocktailFlavourProfile cf WHERE cf.cocktail.id = c.id AND cf.flavourProfile IN :flavourNames GROUP BY cf.cocktail.id HAVING COUNT(cf.cocktail.id) = :numberOfFlavours) " +
            "AND :numberOfIngredients = 0 OR EXISTS (SELECT 1 FROM CocktailNonAlcoholicIngredient cna WHERE cna.cocktail.id = c.id AND cna.nonAlcoholicIngredient.id IN :ingredientIds GROUP BY cna.cocktail.id HAVING COUNT(cna.cocktail.id) = :numberOfIngredients) ")
    List<Cocktail> findCocktailsByCriteria(
            @Param("alcoholIds") List<Long> alcoholIds,
            @Param("numberOfAlcohols") int numberOfAlcohols,
            @Param("flavourNames") List<FlavourProfile> flavourNames,
            @Param("numberOfFlavours") int numberOfFlavours,
            @Param("ingredientIds") List<Long> ingredientIds,
            @Param("numberOfIngredients") int numberOfIngredients);
}
