package com.example.Bartendly.model.DTO;

import com.example.Bartendly.model.MeasurementUnit;
import com.example.Bartendly.model.NonAlcoholicIngredient;

public record NonAlcoholicIngredientInCocktailDTO(NonAlcoholicIngredient nonAlcoholicIngredient, double quantity, MeasurementUnit measurementUnit) {
}
