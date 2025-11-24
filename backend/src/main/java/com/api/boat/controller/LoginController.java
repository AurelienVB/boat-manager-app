package com.api.boat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping
    public ResponseEntity<HttpStatus> getLoginAcknowledge() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
