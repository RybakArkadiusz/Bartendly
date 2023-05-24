package com.example.Bartendly.controller;

import com.example.Bartendly.model.AlcoholType;
import com.example.Bartendly.model.DTO.AlcoholDTO;
import com.example.Bartendly.model.DTO.NonAlcoholicIngredientDTO;
import com.example.Bartendly.model.NonAlcoholicType;
import com.example.Bartendly.model.controller.NonAlcoholicIngredientController;
import com.example.Bartendly.model.service.NonAlcoholicIngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NonAlcoholicIngredientControllerTest {
    @InjectMocks
    NonAlcoholicIngredientController controller;

    @Mock
    NonAlcoholicIngredientService service;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void testFindAll() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER)));

        mockMvc.perform(get("/nonAlcoholicIngredient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll();
    }


    @Test
    public void testFindById() throws Exception {
        when(service.findById(1L)).thenReturn(new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER));

        mockMvc.perform(get("/nonAlcoholicIngredient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).findById(1L);
    }

    @Test
    public void testCreate() throws Exception {
        when(service.create(any(NonAlcoholicIngredientDTO.class))).thenReturn(new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER));

        mockMvc.perform(post("/nonAlcoholicIngredient")
                        .content(mapper.writeValueAsString(new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).create(any(NonAlcoholicIngredientDTO.class));
    }

    @Test
    public void testUpdate() throws Exception {
        when(service.update(anyLong(), any(NonAlcoholicIngredientDTO.class))).thenReturn(new NonAlcoholicIngredientDTO(1L,"name", NonAlcoholicType.OTHER));

        mockMvc.perform(put("/nonAlcoholicIngredient/1")
                        .content(mapper.writeValueAsString(new AlcoholDTO(1L,"name", AlcoholType.OTHER)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).update(anyLong(), any(NonAlcoholicIngredientDTO.class));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/nonAlcoholicIngredient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }

}