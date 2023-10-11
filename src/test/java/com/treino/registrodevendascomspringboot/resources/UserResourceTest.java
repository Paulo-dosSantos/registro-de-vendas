package com.treino.registrodevendascomspringboot.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.services.UserService;

@SpringBootTest
class UserResourceTest {
	
	private static final String PHONE = "2198233459";

	
	private static final String PASSWORD = "poscrise";

	private static final String EMAIL = "coringuete@gmail.com";

	private static final String NAME = "Bruce Wayne";

	private static final Long ID = 1L;
	
	@InjectMocks
	private UserResource resource;
	
	@Mock
	private UserService service;
	
	@Mock 
	private ModelMapper mapper;
	
	private User user;
	
	private UserDTO userDTO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}



	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		user=new User(ID, NAME, EMAIL,PHONE, PASSWORD);
		userDTO= new UserDTO(1L, NAME, EMAIL,PHONE, PASSWORD);
		
	}

	@Test
	void testFindAll() {
		when(service.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>>response=resource.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(ArrayList.class,response.getBody().getClass());
		assertEquals(UserDTO.class,response.getBody().get(0).getClass());
		assertEquals(ID,response.getBody().get(0).getId());
		assertEquals(NAME,response.getBody().get(0).getName());
		assertEquals(EMAIL,response.getBody().get(0).getEmail());
		assertEquals(PASSWORD,response.getBody().get(0).getPassword());
		
	}

	@Test
	void testUpdate() {
		when(service.update(anyLong(),any())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		ResponseEntity<UserDTO>response=resource.update(ID, userDTO);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(UserDTO.class,response.getBody().getClass());
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(ID,response.getBody().getId());
		assertEquals(NAME,response.getBody().getName());
		assertEquals(EMAIL,response.getBody().getEmail());
		assertEquals(PASSWORD,response.getBody().getPassword());
	}

	@Test
	void testFindById() {
		
		when(service.findById(anyLong())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO>response=resource.findById(ID);
		
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(UserDTO.class,response.getBody().getClass());
		assertEquals(ID,response.getBody().getId());
		assertEquals(NAME,response.getBody().getName());
		assertEquals(EMAIL,response.getBody().getEmail());
		assertEquals(PASSWORD,response.getBody().getPassword());
		assertEquals(PHONE,response.getBody().getPhone());
	}

	@Test
	void testDelete() {
		
		doNothing().when(service).delete(anyLong());
		ResponseEntity<UserDTO>response=resource.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class,response.getClass());
		verify(service,times(1)).delete(anyLong());
		assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
		
	
	
	
	}

	@Test
	void testInsert() {
		
		when(service.insert(any())).thenReturn(user);
		
		ResponseEntity<User>response=resource.insert(user);
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		assertNotNull(response.getHeaders().get("Location"));
		assertEquals(ResponseEntity.class,response.getClass());
	}


}
