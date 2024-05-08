package com.resired.api.security.infraestructure.rest.proxy;

import com.resired.api.security.application.exception.InactiveUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerResired {

    @ExceptionHandler(value = InactiveUserException.class)
    protected ResponseEntity<ErrorDTO> handleInactiveUser(
        RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.USER01.name()
            , ErrorCode.USER01.getDescription()), HttpStatus.FORBIDDEN);
    }

}
