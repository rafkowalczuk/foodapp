package pl.polsl.foodapp.controller.advice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException() {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Cannot delete object, because of existing database connection");

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> constraintViolationException() {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Invalid data");

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Validation Error " + ex.getMessage());

    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<String> responseStatusException(ResponseStatusException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error " + ex.getReason());

    }



}
