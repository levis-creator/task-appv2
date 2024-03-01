package com.micosoft.simpletaskapp.errors;

import com.micosoft.simpletaskapp.errors.exceptions.AlreadyExistException;
import com.micosoft.simpletaskapp.errors.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class CusExeptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception){
        CusException notFoundException= new CusException(
                exception.getMessage(),
                exception.getCause(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(notFoundException, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleAlreadyExists(AlreadyExistException exception){
        CusException notFoundException= new CusException(
                exception.getMessage(),
                exception.getCause(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(notFoundException, HttpStatus.NOT_FOUND);
    }


}
