package com.example.Bartendly.controller;

import com.example.Bartendly.model.AlcoholType;
import com.example.Bartendly.model.DTO.AlcoholDTO;
import com.example.Bartendly.model.controller.AlcoholController;
import com.example.Bartendly.model.service.AlcoholService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlcoholControllerTest {

    @InjectMocks
    AlcoholController alcoholController;

    @Mock
    AlcoholService alcoholService;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alcoholController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        when(alcoholService.findAll()).thenReturn(Arrays.asList(new AlcoholDTO(1L,"name", AlcoholType.OTHER)));

        mockMvc.perform(get("/alcohol")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(alcoholService, times(1)).findAll();
    }

    @Test
    public void testFindById() throws Exception {
        when(alcoholService.findById(1L)).thenReturn(new AlcoholDTO(1L,"name", AlcoholType.OTHER));

        mockMvc.perform(get("/alcohol/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(alcoholService, times(1)).findById(1L);
    }

    @Test
    public void testCreate() throws Exception {
        when(alcoholService.create(any(AlcoholDTO.class))).thenReturn(new AlcoholDTO(1L,"name", AlcoholType.OTHER));

        mockMvc.perform(post("/alcohol")
                        .content(mapper.writeValueAsString(new AlcoholDTO(1L,"name", AlcoholType.OTHER)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(alcoholService, times(1)).create(any(AlcoholDTO.class));
    }

    @Test
    public void testUpdate() throws Exception {
        when(alcoholService.update(anyLong(), any(AlcoholDTO.class))).thenReturn(new AlcoholDTO(1L,"name", AlcoholType.OTHER));

        mockMvc.perform(put("/alcohol/1")
                        .content(mapper.writeValueAsString(new AlcoholDTO(1L,"name", AlcoholType.OTHER)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(alcoholService, times(1)).update(anyLong(), any(AlcoholDTO.class));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(alcoholService).delete(1L);

        mockMvc.perform(delete("/alcohol/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(alcoholService, times(1)).delete(1L);
    }
}