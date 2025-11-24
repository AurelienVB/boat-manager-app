package com.api.boat.service;

import com.api.boat.entity.Boat;
import com.api.boat.exception.NotFoundException;
import com.api.boat.repository.BoatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BoatService {
    
    private BoatRepository boatRepository;
    
    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }
    
    public List<Boat> getAllBoats() {
        return boatRepository.findAll();
    }
    
    public Boat getBoatById(Long id) {
        return boatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Boat", id));
    }
    
    public Boat createBoat(Boat boatData) {
        Boat boat = new Boat(boatData.getName(), boatData.getDescription());
        return boatRepository.save(boat);
    }
    
    public Boat updateBoat(Long id, Boat boatData) {
        Boat boat = getBoatById(id);        
        boat.setName(boatData.getName());
        boat.setDescription(boatData.getDescription());        
        return boatRepository.save(boat);
    }
    
    public void deleteBoat(Long id) {
        Boat boat = getBoatById(id);        
        boatRepository.delete(boat);
    }
}
