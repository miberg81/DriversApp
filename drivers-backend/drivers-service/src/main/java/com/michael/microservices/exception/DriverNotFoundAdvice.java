package com.michael.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// used to render an HTTP 404 (page not found) in case DriverNotFoundException exception is thrown
@ControllerAdvice
public class DriverNotFoundAdvice {

	  @ResponseBody
	  @ExceptionHandler(DriverNotFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String driverNotFoundHandler(DriverNotFoundException ex) {
	    return ex.getMessage();
	  }
}
