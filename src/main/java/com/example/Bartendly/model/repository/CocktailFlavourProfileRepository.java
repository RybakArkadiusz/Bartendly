package com.example.Bartendly.model.repository;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.Cocktail;
import com.example.Bartendly.model.FlavourProfile;
import com.example.Bartendly.model.db.CocktailAlcohol;
import com.example.Bartendly.model.db.CocktailFlavourProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CocktailFlavourProfileRepository extends JpaRepository {
    List<CocktailFlavourProfile> findByFlavourProfile(FlavourProfile flavourProfile);
    List<CocktailFlavourProfile> findByCocktail(Cocktail cocktail);
}
