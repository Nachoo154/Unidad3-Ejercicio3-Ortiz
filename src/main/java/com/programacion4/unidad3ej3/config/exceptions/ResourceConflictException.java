package com.programacion4.unidad3ej3.config.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ResourceConflictException extends CustomException {

    public ResourceConflictException(String message) {
        super(message, HttpStatus.CONFLICT, List.of(message));
    }
}
