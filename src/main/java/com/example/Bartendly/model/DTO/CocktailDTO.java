package com.example.Bartendly.model.DTO;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.FlavourProfile;
import com.example.Bartendly.model.NonAlcoholicIngredient;
import com.example.Bartendly.model.PreparationMethod;

import java.util.List;

public record CocktailDTO(Long id, String name, PreparationMethod preparationMethod, String recipe,
                          List<Alcohol> alcohols, List<NonAlcoholicIngredient> nonAlcoholicIngredients,
                          List<FlavourProfile> flavourProfiles) {
}
