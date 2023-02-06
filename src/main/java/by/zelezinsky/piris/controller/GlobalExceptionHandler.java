package by.zelezinsky.piris.controller;

import by.zelezinsky.piris.dto.exception.message.ExceptionMessageDto;
import by.zelezinsky.piris.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessageDto> notFound(RuntimeException exception) {
        ExceptionMessageDto dto = new ExceptionMessageDto(exception.getMessage(), exception.toString());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessageDto> badRequest(RuntimeException exception) {
        ExceptionMessageDto dto = new ExceptionMessageDto(exception.getMessage(), exception.toString());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
