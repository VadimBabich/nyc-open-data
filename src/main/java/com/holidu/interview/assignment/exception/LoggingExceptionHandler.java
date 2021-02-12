package com.holidu.interview.assignment.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Logging and masking of errors that will be reported to the client
 * @author Vadim Babich
 */
@ControllerAdvice
public class LoggingExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(BadArgumentException.class)
    public ResponseEntity<Object> handleBadArgumentException(BadArgumentException ex, WebRequest request) {
        logger.error("Spring MVC exception occurred", ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceExecutingException.class)
    public ResponseEntity<Object> handleServiceExecutingException(ServiceExecutingException ex, WebRequest request) {
        logger.error("Spring MVC exception occurred", ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Spring MVC exception occurred", ex);
        return new ResponseEntity<>(new ErrorResponse("Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
