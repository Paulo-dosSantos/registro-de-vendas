package com.treino.registrodevendascomspringboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.repositories.UserRepository;
import com.treino.registrodevendascomspringboot.services.exceptions.DataException;
import com.treino.registrodevendascomspringboot.services.exceptions.DataIntegrityViolationException;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class UserServiceTest {
	
	private static final String EXCEPTION_NUMBER = "o número de telefone deve ter até 10 caracteres";

	private static final String EXCEPTION_EMAIL = "Este e-mail já está cadastrado";

	private static final String EXCEPTION_OBJECT_NOT_FOUND = "Objeto não encontrado";

	private static final String PASSWORD = "hellblazer";

	private static final String PHONE = "2198349389";

	private static final String EMAIL = "johnzinho@gmail.com";

	private static final String NAME = "John Constantine";

	private static final long ID = 1L;
	
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	private User user;
	
	private UserDTO userDTO;
	
	private Optional<User>optionalUser;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		user=new User(ID, NAME,
				EMAIL, PHONE, PASSWORD);
		userDTO=new UserDTO(ID, NAME,
				EMAIL, PHONE, PASSWORD);
		optionalUser=Optional.of(new User(ID, NAME,
				EMAIL, PHONE, PASSWORD));
		
	}

	@Test
	void testFindAll() {
		when(repository.findAll()).thenReturn(List.of(user));
		List<User>response=service.findAll();
		
		assertNotNull(response);
		assertEquals(1,response.size());
		assertEquals(User.class,response.get(0).getClass());
		
		assertEquals(ID,response.get(0).getId());
		assertEquals(NAME,response.get(0).getName());
		assertEquals(EMAIL,response.get(0).getEmail());
		assertEquals(PHONE,response.get(0).getPhone());
		assertEquals(PASSWORD,response.get(0).getPassword());
		
	}

	@Test
	void testFindById() {
		when(repository.findById(anyLong())).thenReturn(optionalUser);
		
		User response=service.findById(ID);
		
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
	    assertEquals(EMAIL, response.getEmail());
	    assertEquals(PASSWORD,response.getPassword());
	    assertEquals(PHONE,response.getPhone());
	}

	@Test
	void testUpdate() {
		when(repository.save(any())).thenReturn(user);
		
		User response=service.update(ID,userDTO);
		
		assertNotNull(response);
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(PASSWORD,response.getPassword());
		assertEquals(PHONE,response.getPhone());
		assertEquals(EMAIL,response.getEmail());
		assertEquals(User.class,response.getClass());
	}

	@Test
	void testDelete() {
		when(repository.findById(anyLong())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(ID);
		service.delete(ID);
		verify(repository,times(1)).deleteById(anyLong());
	}

	@Test
	void testInsert() {
		when(repository.save(any())).thenReturn(user);
		User response=service.insert(user);
		
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(EMAIL,response.getEmail());
		assertEquals(PASSWORD,response.getPassword());
		assertEquals(PHONE,response.getPhone());
	}
	@Test
	public void testFindByIdObjectNotFoundException() {
		 when(repository.findById(anyLong())).thenThrow(
				 new ObjectNotFoundException(EXCEPTION_OBJECT_NOT_FOUND));
		 
		 try {
			 service.findById(ID);
			 
		 }
		 catch(Exception e) {
			 assertEquals(ObjectNotFoundException.class,e.getClass());
			 assertEquals(EXCEPTION_OBJECT_NOT_FOUND,e.getMessage());
			 
		 }
	}
	@Test
	public void testUpdateDataIntegrityViolationExeption() {
		when(repository.findByEmail(anyString()))
		.thenThrow(new DataIntegrityViolationException
				("Este e-mail já está cadastrado"));
		
		try {
			optionalUser.get().setId(2L);
			service.insert(user);
			
		}
		catch(Exception ex) {
			assertEquals(DataIntegrityViolationException.class,ex.getClass());
			assertEquals(EXCEPTION_EMAIL,ex.getMessage());
		}
		
		
	}
	@Test
	public void testDeleteObjectNotFoundException() {
		 when(repository.findById(anyLong())).thenThrow(
				 new ObjectNotFoundException(EXCEPTION_OBJECT_NOT_FOUND));
		 
		 try {
			 service.delete(ID);
			 
		 }
		 catch(Exception e) {
			 assertEquals(ObjectNotFoundException.class,e.getClass());
			 assertEquals(EXCEPTION_OBJECT_NOT_FOUND,e.getMessage());
			 
		 }
	}
	@Test
	public void testNumberAnalyze() {
		when(repository.save(any())).thenThrow(new DataException
				(EXCEPTION_NUMBER));
		
		try {
			repository.save(user);
		}
		catch(Exception ex) {
			assertEquals(DataException.class,ex.getClass());
			assertEquals(EXCEPTION_NUMBER,ex.getMessage());
			
		}
	}

}
