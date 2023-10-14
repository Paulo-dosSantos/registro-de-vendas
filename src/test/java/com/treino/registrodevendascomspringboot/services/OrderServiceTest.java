package com.treino.registrodevendascomspringboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.treino.registrodevendascomspringboot.entities.Order;
import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.entities.enums.OrderStatus;
import com.treino.registrodevendascomspringboot.repositories.OrderRepository;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

class OrderServiceTest {
	
	private static final String OBJECT_NOT_FOUND = "Objeto não encontrado";

	@InjectMocks
	private OrderService service;
	
	@Mock
	private OrderRepository repository;
	
	private Order order1;
	
	private Order order2;
	private Order order3;
	
	private User usuario1;
	private	User usuario2;
	
	private Optional<Order>optionalOrder;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		 
		 usuario1=new User(1L, "John Constantine",
					"johnzinho@gmail.com", "2194938932", "hellblazer");
			
		usuario2=new User(2L, "Bruce Wayne",
				"waynetech@gmail.com", "2194252545", "coringa_de_calcinha");
		
		order1 = new Order(1L, Instant.parse("2019-06-20T19:53:07Z"), usuario1,OrderStatus.PAID);
		
		 order2 = new Order(2L, Instant.parse("2019-07-21T03:42:10Z"), usuario2,
				 OrderStatus.WAITING_PAYMENT);
		 order3 = new Order(3L, Instant.parse("2019-07-22T15:21:22Z"), usuario1, 
				 OrderStatus.WAITING_PAYMENT); 
		 
		 optionalOrder = Optional.of(new Order(1L, Instant.parse("2019-06-20T19:53:07Z"), 
				 usuario1,OrderStatus.PAID));
	}

	@Test
	void testFindAll() {
		when(repository.findAll())
		.thenReturn(List.of(order1,order2,order3));
		
		List<Order>response=service.findAll();
		
		assertNotNull(response);
		assertEquals(3,response.size());
		assertEquals(Order.class,response.get(0).getClass());
		assertEquals(UserDTO.class,response.get(0).getClient().getClass());
		
	}

	@Test
	void testFindById() {
		when(repository.findById(anyLong())).thenReturn(optionalOrder);
		
		Order response=service.findById(1L);
		assertNotNull(response);
		assertEquals(Order.class,response.getClass());
		assertEquals(response.getId(),order1.getId());
		assertEquals(response.getOrderStatus(),order1.getOrderStatus());
		assertEquals(response.getMoment(),order1.getMoment());
		assertEquals(UserDTO.class,response.getClient().getClass());
		
	}
	@Test
	public void testObjectNotFoundException() {
		when(repository.findById(anyLong())).
		thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));
	
		try {
			service.findById(1L);
		}
		catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class,ex.getClass());
			assertEquals(OBJECT_NOT_FOUND,ex.getMessage());
			
		}
	
	
	
	
	
	
	
	
	
	
	}

}
