package com.resired.api.security.infraestructure.rest.proxy;

import com.resired.api.guard.application.exception.QrInvalidException;
import com.resired.api.guard.domain.exception.HomeNotFoundException;
import com.resired.api.resident.domain.exception.InvalidVisitorException;
import com.resired.api.security.application.exception.ExpiredTokenException;
import com.resired.api.security.application.exception.InvalidCredentialException;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.shared.notification.domain.exception.DeviceAlreadyExistsException;
import com.resired.api.shared.notification.domain.exception.DeviceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(value = AuthorizationDeniedException.class)
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

    @ExceptionHandler(value = HomeNotFoundException.class)
    protected ResponseEntity<ErrorDTO> handleHomeNotFoundException(
        RuntimeException ex, WebRequest request) {
        log.error("Problems with home ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.HOME01.name()
            , ErrorCode.HOME01.getDescription()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorDTO> unexpected(Exception ex, WebRequest request) {
        log.error("unexpected error ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.GENERAL.name()
            , ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    protected ResponseEntity<ErrorDTO> unMappedResource(Exception ex, WebRequest request) {
        log.error("Invalid url request ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.GENERAL_RESOURCE.name()
            , ErrorCode.GENERAL_RESOURCE.getDescription()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidVisitorException.class)
    protected ResponseEntity<ErrorDTO> handleInvalidVisitor(
        RuntimeException ex, WebRequest request) {
        log.error("The visitor is invalid ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.VISIT02.name()
            , ErrorCode.VISIT02.getDescription()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DeviceAlreadyExistsException.class)
    protected ResponseEntity<ErrorDTO> handleAlreadyExistsDevice(
        RuntimeException ex, WebRequest request) {
        log.error("The device already exists ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.DEVICE01.name(),
            ErrorCode.DEVICE01.getDescription()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = DeviceNotFoundException.class)
    protected ResponseEntity<ErrorDTO> handleDeviceForUserNotFound(
        RuntimeException ex, WebRequest request) {
        log.error("Device not found ", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.DEVICE02.name(),
            ErrorCode.DEVICE02.getDescription()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ExpiredTokenException.class)
    protected ResponseEntity<ErrorDTO> handlerTokExpiredToken(
        RuntimeException ex, WebRequest request) {
        log.error("Invalid Token because is Expired", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.USER04.name(),
            ErrorCode.USER04.getDescription()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorDTO> handlerBadRequest(
        RuntimeException ex, WebRequest request) {
        log.error("Invalid request", ex);
        return new ResponseEntity<>(new ErrorDTO(ErrorCode.GENERAL_BAD_REQUEST.name(),
            ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
