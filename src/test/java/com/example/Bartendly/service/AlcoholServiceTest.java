package com.example.Bartendly.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.Bartendly.model.Alcohol;
import com.example.Bartendly.model.AlcoholType;
import com.example.Bartendly.model.DTO.AlcoholDTO;
import com.example.Bartendly.model.repository.AlcoholRepository;
import com.example.Bartendly.model.service.AlcoholService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AlcoholServiceTest {

    @InjectMocks
    AlcoholService alcoholService;

    @Mock
    AlcoholRepository alcoholRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Alcohol alcohol1 = new Alcohol();
        Alcohol alcohol2 = new Alcohol();
        List<Alcohol> alcoholList = Arrays.asList(alcohol1, alcohol2);
        when(alcoholRepository.findAll()).thenReturn(alcoholList);

        List<AlcoholDTO> result = alcoholService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        Alcohol alcohol = new Alcohol();
        when(alcoholRepository.findById(1L)).thenReturn(Optional.of(alcohol));

        AlcoholDTO result = alcoholService.findById(1L);

        assertNotNull(result);
    }

    @Test
    public void testFindById_NotFound() {
        when(alcoholRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> alcoholService.findById(1L));
    }

    @Test
    public void testCreate() {
        AlcoholDTO alcoholDTO = new AlcoholDTO(1L, "name", AlcoholType.OTHER);
        Alcohol alcohol = new Alcohol();
        when(alcoholRepository.save(any(Alcohol.class))).thenReturn(alcohol);

        AlcoholDTO result = alcoholService.create(alcoholDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdate() {
        AlcoholDTO alcoholDTO = new AlcoholDTO(1L, "name", AlcoholType.OTHER);
        Alcohol alcohol = new Alcohol();
        when(alcoholRepository.findById(1L)).thenReturn(Optional.of(alcohol));
        when(alcoholRepository.save(any(Alcohol.class))).thenReturn(alcohol);

        AlcoholDTO result = alcoholService.update(1L, alcoholDTO);

        assertNotNull(result);
    }

    @Test
    public void testUpdate_NotFound() {
        AlcoholDTO alcoholDTO = new AlcoholDTO(1L, "name", AlcoholType.OTHER);
        when(alcoholRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> alcoholService.update(1L, alcoholDTO));
    }

    @Test
    public void testDelete() {
        Alcohol alcohol = new Alcohol();
        when(alcoholRepository.findById(1L)).thenReturn(Optional.of(alcohol));

        alcoholService.delete(1L);

        verify(alcoholRepository, times(1)).delete(alcohol);
    }

    @Test
    public void testDelete_NotFound() {
        when(alcoholRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> alcoholService.delete(1L));
    }
}
