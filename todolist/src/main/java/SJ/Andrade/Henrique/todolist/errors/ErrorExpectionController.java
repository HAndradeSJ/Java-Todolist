package  SJ.Andrade.Henrique.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorExpectionController {
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<String>handleHttpMessageNotReadableExcpetion(HttpMessageConversionException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
    }

}