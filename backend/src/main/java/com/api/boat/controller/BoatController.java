package com.api.boat.controller;

import com.api.boat.entity.Boat;
import com.api.boat.service.BoatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boats")
public class BoatController {
    
    private BoatService boatService;
    
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }
    
    @GetMapping
    public ResponseEntity<List<Boat>> getAllBoats() {
        List<Boat> boats = boatService.getAllBoats();
        return new ResponseEntity<>(boats, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Boat> getBoatById(@PathVariable Long id) {
        Boat boat = boatService.getBoatById(id);
        return new ResponseEntity<>(boat, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Boat> createBoat(@RequestBody Boat boatData) {
        Boat boat = boatService.createBoat(boatData);
        return new ResponseEntity<>(boat, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Boat> updateBoat(@PathVariable Long id, @RequestBody Boat boatData) {
        Boat boat = boatService.updateBoat(id, boatData);
        return new ResponseEntity<>(boat, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBoat(@PathVariable Long id) {
        boatService.deleteBoat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
