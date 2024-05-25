package com.resired.api.security.infraestructure.rest.proxy;

import com.resired.api.guard.domain.exception.InvalidRolException;
import com.resired.api.guard.application.exception.QrInvalidException;
import com.resired.api.security.domain.exception.InactiveUserException;
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
        log.error("Problems with user ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.USER01.name()
            , ErrorCode.USER01.getDescription()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = InvalidCredentialException.class)
    protected ResponseEntity<ErrorDTO> handleInvalidCredentials(
        RuntimeException ex, WebRequest request) {
        log.error("Problems with credential ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.USER02.name()
            , ErrorCode.USER02.getDescription()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = InvalidRolException.class)
    protected ResponseEntity<ErrorDTO> handleInvalidRol(
        RuntimeException ex, WebRequest request) {
        log.error("Problems with role ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.USER03.name()
            , ErrorCode.USER03.getDescription()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = QrInvalidException.class)
    protected ResponseEntity<ErrorDTO> handleQRInvalidException(
        RuntimeException ex, WebRequest request) {
        log.error("Problems with qr ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.VISIT01.name()
            , ErrorCode.VISIT01.getDescription()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorDTO> unexpected(Exception ex, WebRequest request) {
        log.error("unexpected error ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.GENERAL.name()
            , ErrorCode.GENERAL.getDescription()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
