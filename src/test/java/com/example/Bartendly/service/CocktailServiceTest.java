package com.example.Bartendly.service;

import com.example.Bartendly.model.*;
import com.example.Bartendly.model.DTO.*;
import com.example.Bartendly.model.repository.*;
import com.example.Bartendly.model.service.AlcoholService;
import com.example.Bartendly.model.service.CocktailService;
import com.example.Bartendly.model.service.NonAlcoholicIngredientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CocktailServiceTest {

    @InjectMocks
    CocktailService cocktailService;

    @Mock
    CocktailRepository cocktailRepository;

    @Mock
    CocktailAlcoholRepository cocktailAlcoholRepository;

    @Mock
    CocktailFlavourProfileRepository cocktailFlavourProfileRepository;

    @Mock
    CocktailNonAlcoholicIngredientRepository cocktailNonAlcoholicIngredientRepository;

    @Mock
    AlcoholRepository alcoholRepository;

    @Mock
    NonAlcoholicIngredientRepository nonAlcoholicIngredientRepository;

    @Mock
    AlcoholService alcoholService;

    @Mock
    NonAlcoholicIngredientService nonAlcoholicIngredientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Cocktail cocktail1 = new Cocktail();
        Cocktail cocktail2 = new Cocktail();
        List<Cocktail> cocktailList = Arrays.asList(cocktail1, cocktail2);
        when(cocktailRepository.findAll()).thenReturn(cocktailList);

        List<CocktailDTO> result = cocktailService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        Cocktail cocktail = new Cocktail();
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));

        CocktailDTO result = cocktailService.findById(1L);

        assertNotNull(result);
    }

    @Test
    public void testFindById_NotFound() {
        when(cocktailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cocktailService.findById(1L));
    }

    @Test
    public void testCreate() {
        Alcohol alcoholEntity = new Alcohol(1L,"name",AlcoholType.OTHER);
        AlcoholInCocktailDTO alcohol = new AlcoholInCocktailDTO(alcoholEntity, 12.0, MeasurementUnit.DASH);
        when(alcoholRepository.findById(1L)).thenReturn(Optional.of(alcoholEntity));

        NonAlcoholicIngredient nonAlcoEntity = new NonAlcoholicIngredient(1L,"name",NonAlcoholicType.OTHER);
        NonAlcoholicIngredientInCocktailDTO nonAlco = new NonAlcoholicIngredientInCocktailDTO(nonAlcoEntity, 12.0, MeasurementUnit.DASH);
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.of(nonAlcoEntity));

        CocktailDTO cocktailDTO = new CocktailDTO(1L, "name", PreparationMethod.BLENDING, "recipe", List.of(alcohol), List.of(nonAlco), List.of(FlavourProfile.BITTER));

        Cocktail cocktail = new Cocktail();
        when(cocktailRepository.save(any(Cocktail.class))).thenReturn(cocktail);

        CocktailDTO result = cocktailService.create(cocktailDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdate() {

        Alcohol alcoholEntity = new Alcohol(1L, "name", AlcoholType.OTHER);
        AlcoholInCocktailDTO alcohol = new AlcoholInCocktailDTO(alcoholEntity, 12.0, MeasurementUnit.DASH);
        when(alcoholRepository.findById(1L)).thenReturn(Optional.of(alcoholEntity));

        NonAlcoholicIngredient nonAlcoEntity = new NonAlcoholicIngredient(1L, "name", NonAlcoholicType.OTHER);
        NonAlcoholicIngredientInCocktailDTO nonAlco = new NonAlcoholicIngredientInCocktailDTO(nonAlcoEntity, 12.0, MeasurementUnit.DASH);
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.of(nonAlcoEntity));

        CocktailDTO cocktailDTO = new CocktailDTO(1L, "name", PreparationMethod.BLENDING, "recipe", List.of(alcohol), List.of(nonAlco), List.of(FlavourProfile.BITTER));

        Cocktail cocktail = new Cocktail();
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));
        when(cocktailRepository.save(any(Cocktail.class))).thenReturn(cocktail);

        // When
        CocktailDTO result = cocktailService.update(1L, cocktailDTO);

        // Then
        assertNotNull(result);
    }

    @Test
    public void testUpdate_NotFound() {

        AlcoholInCocktailDTO alcohol = new AlcoholInCocktailDTO(new Alcohol(1L,"name",AlcoholType.OTHER),12.0,MeasurementUnit.DASH);
        NonAlcoholicIngredientInCocktailDTO nonAlco = new NonAlcoholicIngredientInCocktailDTO(new NonAlcoholicIngredient(1L,"name",NonAlcoholicType.OTHER),12.0,MeasurementUnit.DASH);
        CocktailDTO cocktailDTO = new CocktailDTO(1L,"name", PreparationMethod.BLENDING, "recipe", List.of(alcohol),List.of(nonAlco),List.of(FlavourProfile.BITTER));


        when(cocktailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cocktailService.update(1L, cocktailDTO));
    }

    @Test
    public void testDelete() {
        Cocktail cocktail = new Cocktail();
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));

        cocktailService.delete(1L);

        verify(cocktailRepository, times(1)).delete(cocktail);
    }
}