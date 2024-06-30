package ar.utn.frbb.tup.controller.handler;

import ar.utn.frbb.tup.business.exception.*;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NombreInvalidoException.class})
    protected ResponseEntity<Object> handleNombreInvalidoException(NombreInvalidoException ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(exceptionMessage);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {CantidadCuatrimestresInvalidaException.class})
    protected ResponseEntity<Object> handleCantidadCuatrimestresInvalidaException(CantidadCuatrimestresInvalidaException ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(exceptionMessage);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DatoNumericoInvalidoException.class})
    protected ResponseEntity<Object> handleDatoNumericoInvalidoException(DatoNumericoInvalidoException ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(exceptionMessage);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(value = {NoEncontradoException.class})
    protected ResponseEntity<Object> handleNoEncontradoException(NoEncontradoException ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(exceptionMessage);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(exceptionMessage);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            CustomApiError error = new CustomApiError();
            error.setErrorMessage(ex.getMessage());
            body = error;
        }

        return new ResponseEntity(body, headers, status);
    }
}
