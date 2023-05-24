package com.example.Bartendly.service;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.DTO.NonAlcoholicIngredientDTO;
import com.example.Bartendly.model.NonAlcoholicIngredient;
import com.example.Bartendly.model.NonAlcoholicType;
import com.example.Bartendly.model.repository.NonAlcoholicIngredientRepository;
import com.example.Bartendly.model.service.NonAlcoholicIngredientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NonAlcoholicIngredientServiceTest {

    @InjectMocks
    NonAlcoholicIngredientService nonAlcoholicIngredientService;
    @Mock
    NonAlcoholicIngredientRepository nonAlcoholicIngredientRepository;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);}
    @Test
    public void testFindAll(){
        NonAlcoholicIngredient nonAlco1 = new NonAlcoholicIngredient();
        NonAlcoholicIngredient nonAlco2 = new NonAlcoholicIngredient();
        when(nonAlcoholicIngredientRepository.findAll()).thenReturn(List.of(nonAlco1,nonAlco2));

        List<NonAlcoholicIngredientDTO> result = nonAlcoholicIngredientService.findAll();

        assertEquals(2,result.size());
    }

    @Test
    public void testFindById(){
        NonAlcoholicIngredient nonAlco = new NonAlcoholicIngredient();
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.of(nonAlco));

        NonAlcoholicIngredientDTO result = nonAlcoholicIngredientService.findById(1L);
        assertNotNull(result);
    }

    @Test
    public void testFindById_NotFound(){
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> nonAlcoholicIngredientService.findById(1L));
    }

    @Test
    public void testCreate(){
        NonAlcoholicIngredientDTO nonAlcoholicIngredientDTO = new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER);
        NonAlcoholicIngredient nonAlcoholicIngredient = new NonAlcoholicIngredient();
        when(nonAlcoholicIngredientRepository.save(any(NonAlcoholicIngredient.class))).thenReturn(nonAlcoholicIngredient);

        NonAlcoholicIngredientDTO result = nonAlcoholicIngredientService.create(nonAlcoholicIngredientDTO);
        assertNotNull(result);
    }

    @Test
    public void testUpdate(){
        NonAlcoholicIngredientDTO nonAlcoholicIngredientDTO = new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER);
        NonAlcoholicIngredient nonAlcoholicIngredient = new NonAlcoholicIngredient();
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.of(nonAlcoholicIngredient));
        when(nonAlcoholicIngredientRepository.save(any(NonAlcoholicIngredient.class))).thenReturn(nonAlcoholicIngredient);

        NonAlcoholicIngredientDTO result = nonAlcoholicIngredientService.update(1L,nonAlcoholicIngredientDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdate_NotFound(){
        NonAlcoholicIngredientDTO nonAlcoholicIngredientDTO = new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER);
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> nonAlcoholicIngredientService.update(1L, nonAlcoholicIngredientDTO));
    }

    @Test
    public void testDelete() {
        NonAlcoholicIngredient nonAlcoholicIngredient = new NonAlcoholicIngredient();
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.of(nonAlcoholicIngredient));

        nonAlcoholicIngredientService.delete(1L);

        verify(nonAlcoholicIngredientRepository, times(1)).delete(nonAlcoholicIngredient);
    }

    @Test
    public void testDelete_NotFound(){
        when(nonAlcoholicIngredientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> nonAlcoholicIngredientService.delete(1L));
    }

}
