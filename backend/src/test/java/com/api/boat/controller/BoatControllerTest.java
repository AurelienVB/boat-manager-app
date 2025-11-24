package com.api.boat.controller;

import com.api.boat.entity.Boat;
import com.api.boat.config.SecurityConfig;
import com.api.boat.exception.NotFoundException;
import com.api.boat.service.BoatService;
import com.api.boat.BoatApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoatController.class)
@ContextConfiguration(classes = {BoatApplication.class, SecurityConfig.class})
class BoatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoatService boatService;

    @Autowired
    private ObjectMapper objectMapper;

    private Boat testBoat;

    @BeforeEach
    void setUp() {
        testBoat = new Boat("Test Boat", "Test Description");
        testBoat.setId(1L);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testGetAllBoats() throws Exception {
        Boat boat1 = new Boat("Boat 1", "Description 1");
        Boat boat2 = new Boat("Boat 2", "Description 2");
        when(boatService.getAllBoats()).thenReturn(Arrays.asList(boat1, boat2));

        mockMvc.perform(get("/boats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Boat 1"))
                .andExpect(jsonPath("$[1].name").value("Boat 2"));
        verify(boatService, times(1)).getAllBoats();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testGetBoatById_Success() throws Exception {
        when(boatService.getBoatById(1L)).thenReturn(testBoat);

        mockMvc.perform(get("/boats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Boat"))
                .andExpect(jsonPath("$.description").value("Test Description"));
        verify(boatService, times(1)).getBoatById(1L);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testGetBoatById_NotFound() throws Exception {
        when(boatService.getBoatById(1L)).thenThrow(new NotFoundException("Boat", 1L));

        mockMvc.perform(get("/boats/1"))
                .andExpect(status().isNotFound());
        verify(boatService, times(1)).getBoatById(1L);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testCreateBoat() throws Exception {
        Boat boatToCreate = new Boat("New Boat", "New Description");
        Boat createdBoat = new Boat("New Boat", "New Description");
        createdBoat.setId(1L);
        
        when(boatService.createBoat(any(Boat.class))).thenReturn(createdBoat);

        mockMvc.perform(post("/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boatToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Boat"));
        verify(boatService, times(1)).createBoat(any(Boat.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testUpdateBoat_Success() throws Exception {
        Boat updatedBoat = new Boat("Updated Boat", "Updated Description");
        updatedBoat.setId(1L);
        
        when(boatService.updateBoat(anyLong(), any(Boat.class))).thenReturn(updatedBoat);

        mockMvc.perform(put("/boats/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBoat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Boat"));
        verify(boatService, times(1)).updateBoat(eq(1L), any(Boat.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testUpdateBoat_NotFound() throws Exception {
        Boat boatDetails = new Boat("Updated Boat", "Updated Description");
        
        when(boatService.updateBoat(anyLong(), any(Boat.class)))
                .thenThrow(new NotFoundException("Boat", 1L));

        mockMvc.perform(put("/boats/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boatDetails)))
                .andExpect(status().isNotFound());
        verify(boatService, times(1)).updateBoat(eq(1L), any(Boat.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDeleteBoat_Success() throws Exception {

        doNothing().when(boatService).deleteBoat(1L);

        mockMvc.perform(delete("/boats/1"))
                .andExpect(status().isNoContent());
        verify(boatService, times(1)).deleteBoat(1L);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDeleteBoat_NotFound() throws Exception {
        doThrow(new NotFoundException("Boat", 1L)).when(boatService).deleteBoat(1L);

        mockMvc.perform(delete("/boats/1"))
                .andExpect(status().isNotFound());
        verify(boatService, times(1)).deleteBoat(1L);
    }
}
