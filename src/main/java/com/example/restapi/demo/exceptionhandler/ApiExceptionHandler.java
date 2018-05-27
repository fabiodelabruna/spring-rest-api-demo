package com.example.restapi.demo.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
        String detailMessage = ex.getCause().toString();
        return handleExceptionInternal(ex, new Error(errorMessage, detailMessage), headers, status, request);
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
