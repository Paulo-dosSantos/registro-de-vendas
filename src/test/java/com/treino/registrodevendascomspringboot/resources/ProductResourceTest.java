package com.treino.registrodevendascomspringboot.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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

import com.treino.registrodevendascomspringboot.entities.Product;
import com.treino.registrodevendascomspringboot.services.ProductService;

@SpringBootTest
class ProductResourceTest {
	
	@InjectMocks
	private ProductResource resource;
	
	@Mock
	private ProductService service;
	
	private Product product;
	
	private static final String IMGURL = "";

	private static final double PRICE = 90.5;

	private static final String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur.";

	private static final String NAME = "The Lord of the Rings";

	private static final long ID = 1L;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		product=new Product( NAME, DESCRIPTION, PRICE, IMGURL); 
		product.setId(ID);
		
	}

	@Test
	void testFindAll() {
		when(service.findAll()).thenReturn(List.of(product));
		
		ResponseEntity<List<Product>>response=resource.findAll();
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(1,response.getBody().size());
		assertEquals(Product.class,response.getBody().get(0).getClass());
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(ID,response.getBody().get(0).getId());
		assertEquals(NAME,response.getBody().get(0).getName());
		assertEquals(DESCRIPTION,response.getBody().get(0).getDescription());
		assertEquals(PRICE,response.getBody().get(0).getPrice());
		assertEquals(IMGURL,response.getBody().get(0).getImgUrl());

	}

	@Test
	void testFindById() {
		when(service.findById(anyLong())).thenReturn(product);
		
		ResponseEntity<Product>response=resource.findById(ID);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(Product.class,response.getBody().getClass());
		assertEquals(ID,response.getBody().getId());
		assertEquals(NAME,response.getBody().getName());
		assertEquals(DESCRIPTION,response.getBody().getDescription());
		assertEquals(PRICE,response.getBody().getPrice());

	}

}
