package com.api.boat.service;

import com.api.boat.entity.Boat;
import com.api.boat.exception.NotFoundException;
import com.api.boat.repository.BoatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BoatServiceTest {

    @Mock
    private BoatRepository boatRepository;

    @InjectMocks
    private BoatService boatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBoats() {
        Boat boat1 = new Boat("Boat 1", "Description 1");
        Boat boat2 = new Boat("Boat 2", "Description 2");
        List<Boat> boats = Arrays.asList(boat1, boat2);        
        when(boatRepository.findAll()).thenReturn(boats);

        List<Boat> result = boatService.getAllBoats();

        assertEquals(2, result.size());
        assertEquals("Boat 1", result.get(0).getName());
        assertEquals("Boat 2", result.get(1).getName());
        verify(boatRepository, times(1)).findAll();
    }

    @Test
    void testGetBoatById_ExistingBoat() {
        Boat boat = new Boat("Test Boat", "Test Description");
        boat.setId(1L);        
        when(boatRepository.findById(1L)).thenReturn(Optional.of(boat));

        Boat result = boatService.getBoatById(1L);

        assertEquals("Test Boat", result.getName());
        verify(boatRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBoatById_NonExistingBoat() {
        when(boatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            boatService.getBoatById(1L);
        });
        verify(boatRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateBoat() {
        Boat boatToCreate = new Boat("New Boat", "New Description");
        Boat savedBoat = new Boat("New Boat", "New Description");
        savedBoat.setId(1L);        
        when(boatRepository.save(any(Boat.class))).thenReturn(savedBoat);

        Boat result = boatService.createBoat(boatToCreate);

        assertEquals(1L, result.getId());
        assertEquals("New Boat", result.getName());
        verify(boatRepository, times(1)).save(any(Boat.class));
    }

    @Test
    void testUpdateBoat_Success() {
        Boat existingBoat = new Boat("Old Name", "Old Description");
        existingBoat.setId(1L);        
        Boat boatDetails = new Boat("New Name", "New Description");        
        when(boatRepository.findById(1L)).thenReturn(Optional.of(existingBoat));
        when(boatRepository.save(any(Boat.class))).thenReturn(existingBoat);

        Boat result = boatService.updateBoat(1L, boatDetails);

        assertEquals("New Name", result.getName());
        assertEquals("New Description", result.getDescription());
        verify(boatRepository, times(1)).findById(1L);
        verify(boatRepository, times(1)).save(existingBoat);
    }

    @Test
    void testUpdateBoat_NotFound() {
        Boat boatDetails = new Boat("New Name", "New Description");        
        when(boatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            boatService.updateBoat(1L, boatDetails);
        });        
        verify(boatRepository, times(1)).findById(1L);
        verify(boatRepository, never()).save(any());
    }

    @Test
    void testDeleteBoat_Success() {
        Boat boat = new Boat("Boat to delete", "Description");
        boat.setId(1L);        
        when(boatRepository.findById(1L)).thenReturn(Optional.of(boat));

        boatService.deleteBoat(1L);

        verify(boatRepository, times(1)).findById(1L);
        verify(boatRepository, times(1)).delete(boat);
    }

    @Test
    void testDeleteBoat_NotFound() {
        when(boatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            boatService.deleteBoat(1L);
        });        
        verify(boatRepository, times(1)).findById(1L);
        verify(boatRepository, never()).delete(any());
    }
}
