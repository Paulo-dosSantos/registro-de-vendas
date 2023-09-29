package com.treino.registrodevendascomspringboot.resources.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.treino.registrodevendascomspringboot.services.exceptions.DataException;
import com.treino.registrodevendascomspringboot.services.exceptions.DataIntegrityViolationException;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError>objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){
		StandardError error= new StandardError
				(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError>dataIntegrityViolationException(
			DataIntegrityViolationException ex, HttpServletRequest request){
		StandardError erro= new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

@ExceptionHandler(DataException.class)
public ResponseEntity<StandardError>dataException(
		DataException ex, HttpServletRequest request){
	StandardError erro= new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
			ex.getMessage(), request.getRequestURI());
	
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
}
}
