package com.example.Bartendly.model.DTO;

import com.example.Bartendly.model.*;
import com.example.Bartendly.model.db.CocktailAlcohol;
import com.example.Bartendly.model.db.CocktailFlavourProfile;
import com.example.Bartendly.model.db.CocktailNonAlcoholicIngredient;
import com.example.Bartendly.model.repository.CocktailAlcoholRepository;
import com.example.Bartendly.model.repository.CocktailFlavourProfileRepository;
import com.example.Bartendly.model.repository.CocktailNonAlcoholicIngredientRepository;

import java.util.List;
import java.util.stream.Collectors;

public record CocktailDTO(Long id, String name, PreparationMethod preparationMethod, String recipe,
                          List<Alcohol> alcohols, List<NonAlcoholicIngredient> nonAlcoholicIngredients,
                          List<FlavourProfile> flavourProfiles) {

}
