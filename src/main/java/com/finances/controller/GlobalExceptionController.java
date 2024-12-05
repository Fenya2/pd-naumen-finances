package com.finances.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс, обрабатывающий все исключения приложения на rest-контроллерах
 */
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        return handleDefault(e);
    }

    private static ResponseEntity<Response> handleDefault(Exception e) {
        Response response = new Response();
        response.setMessage(e.getMessage()); // Сообщение об ошибке из исключения
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

class Response {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
