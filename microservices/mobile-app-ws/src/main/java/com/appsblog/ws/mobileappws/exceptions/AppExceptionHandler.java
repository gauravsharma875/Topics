package com.appsblog.ws.mobileappws.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest webRequest) {

        String localizedMessage = ex.getLocalizedMessage();
        if (localizedMessage == null) localizedMessage = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), localizedMessage);

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(Exception ex, WebRequest webRequest) {

        String localizedMessage = ex.getLocalizedMessage();
        if (localizedMessage == null) localizedMessage = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), localizedMessage);

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
