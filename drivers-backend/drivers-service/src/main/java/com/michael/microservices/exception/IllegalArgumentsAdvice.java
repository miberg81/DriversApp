package com.michael.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IllegalArgumentsAdvice {
	  @ResponseBody
	  @ExceptionHandler(IllegalArgumentException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String illigalArgumentsHandler(IllegalArgumentException ex) {
	    return ex.getMessage();
	  }
}
