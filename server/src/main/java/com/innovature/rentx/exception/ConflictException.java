package com.innovature.rentx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.CONFLICT;;
public class ConflictException extends ResponseStatusException {

    public ConflictException(HttpStatus status, String message) {
        super(status, message);
    }
    public ConflictException(String reason) {
        super(CONFLICT, reason);
    }

}
