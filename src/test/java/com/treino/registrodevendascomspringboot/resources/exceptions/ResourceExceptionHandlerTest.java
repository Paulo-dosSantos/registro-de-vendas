package com.treino.registrodevendascomspringboot.resources.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.treino.registrodevendascomspringboot.services.exceptions.DataException;
import com.treino.registrodevendascomspringboot.services.exceptions.DataIntegrityViolationException;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class ResourceExceptionHandlerTest {
	
	private static final String OBJECT_NOT_FOUND = "Objeto não encontrado";
	private static final String EXCEPTION_EMAIL = "Este e-mail já está cadastrado no sistema";
	private static final String EXCEPTION_PHONE = "o número de telefone deve ter até 10 caracteres";
	
	@InjectMocks
	private ResourceExceptionHandler exceptionHandler;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
	}

	@Test
	void testObjectNotFoundException() {
		ResponseEntity<StandardError>response= exceptionHandler.objectNotFoundException
				(new ObjectNotFoundException(OBJECT_NOT_FOUND), new MockHttpServletRequest());
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(StandardError.class,response.getBody().getClass());
		assertEquals(OBJECT_NOT_FOUND,response.getBody().getError());
		assertEquals(404,response.getBody().getStatus());
		
	}

	@Test
	void testDataIntegrityViolationException() {
		ResponseEntity<StandardError>response=exceptionHandler
				.dataIntegrityViolationException(new DataIntegrityViolationException(EXCEPTION_EMAIL), new MockHttpServletRequest());
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(StandardError.class,response.getBody().getClass());
		assertEquals(EXCEPTION_EMAIL,response.getBody().getError());
		assertEquals(404,response.getBody().getStatus());
		
	}

	@Test
	void testDataException() {
		ResponseEntity<StandardError>response=exceptionHandler
				.dataException(new DataException(EXCEPTION_PHONE), new MockHttpServletRequest());
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(StandardError.class,response.getBody().getClass());
		assertEquals(EXCEPTION_PHONE,response.getBody().getError());
		assertEquals(404,response.getBody().getStatus());
	}

}
