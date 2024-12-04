package com.finances.dto.account;

import com.finances.service.account.AccountNotFoundException;
import com.finances.service.user.UserNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountAdvice {
    @ExceptionHandler({AccountNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<Response> handleException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
