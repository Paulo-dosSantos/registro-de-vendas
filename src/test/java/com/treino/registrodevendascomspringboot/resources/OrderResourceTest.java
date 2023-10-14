package com.treino.registrodevendascomspringboot.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.treino.registrodevendascomspringboot.entities.Order;
import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.entities.enums.OrderStatus;
import com.treino.registrodevendascomspringboot.services.OrderService;
@SpringBootTest
class OrderResourceTest {
	
	@InjectMocks
	private OrderResource resource;
	
	@Mock
	private OrderService service;
	
	private Order order1;
	private Order order2;
	private Order order3;
	
	private User usuario1;
	private	User usuario2;

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
	 
	
	 
		
	}

	@Test
	void testFindAll() {
		when(service.findAll()).thenReturn(List.of(order1,order2,order3));
		
		ResponseEntity<List<Order>>response= resource.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(Order.class,response.getBody().get(0).getClass());
		assertEquals(3,response.getBody().size());
		assertEquals(Instant.class,response.getBody().get(0).getMoment().getClass());
		assertEquals(UserDTO.class,response.getBody().get(0).getClient().getClass());
		assertEquals(OrderStatus.class,response.getBody().get(0).getOrderStatus().getClass());
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	void testFindById() {
		when(service.findById(anyLong())).thenReturn(order1);
		
		ResponseEntity<Order>response=resource.findById(2L);
		assertNotNull(response);
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(Order.class,response.getBody().getClass());
		assertEquals(Instant.class,response.getBody().getMoment().getClass());
		assertEquals(UserDTO.class,response.getBody().getClient().getClass());
		assertEquals(OrderStatus.class,response.getBody().getOrderStatus().getClass());
		assertEquals(HttpStatus.OK,response.getStatusCode());
	
	
	}

}
