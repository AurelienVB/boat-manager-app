package com.api.boat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    
    public NotFoundException() {
        super("Resource not found");
    }
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String resourceType, Long id) {
        super(String.format("%s not found with id: %d", resourceType, id));
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
