package com.example.restapi.demo.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
        String detailMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> errors = Arrays.asList(new Error(errorMessage, detailMessage));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(RuntimeException ex, WebRequest request) {
        String errorMessage = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String detailMessage = ex.toString();
        List<Error> errors = Arrays.asList(new Error(errorMessage, detailMessage));
        return handleExceptionInternal(ex, errors, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request);
    }

    private List<Error> createErrorList(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String errorMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String detailMessage = fieldError.toString();
            errors.add(new Error(errorMessage, detailMessage));
        }

        return errors;
    }

    class Error {

        private String errorMessage;
        private String detailMessage;

        public Error(String errorMessage, String detailMessage) {
            this.errorMessage = errorMessage;
            this.detailMessage = detailMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getDetailMessage() {
            return detailMessage;
        }

    }

}
