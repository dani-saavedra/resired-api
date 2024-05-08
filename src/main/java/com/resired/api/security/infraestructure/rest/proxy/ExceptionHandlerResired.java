package com.resired.api.security.infraestructure.rest.proxy;

import com.resired.api.security.application.exception.InactiveUserException;
import com.resired.api.security.application.exception.InvalidCredentialException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerResired {

    @ExceptionHandler(value = InactiveUserException.class)
    protected ResponseEntity<ErrorDTO> handleInactiveUser(
        RuntimeException ex, WebRequest request) {
        log.error("Problems with user ",ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.USER01.name()
            , ErrorCode.USER01.getDescription()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = InvalidCredentialException.class)
    protected ResponseEntity<ErrorDTO> handleInvalidCredentials(
        RuntimeException ex, WebRequest request) {
        log.error("Problems with credential ",ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.USER02.name()
            , ErrorCode.USER02.getDescription()), HttpStatus.FORBIDDEN);
    }


}
