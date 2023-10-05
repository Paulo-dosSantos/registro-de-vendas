package com.treino.registrodevendascomspringboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.treino.registrodevendascomspringboot.entities.Product;
import com.treino.registrodevendascomspringboot.repositories.ProductRepository;

class ProductServiceTest {
	
	private static final String IMGURL = "";

	private static final double PRICE = 90.5;

	private static final String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur.";

	private static final String NAME = "The Lord of the Rings";

	private static final long ID = 1L;

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private Product product;
	private Optional<Product> optionalProduct;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		product=new Product(ID, NAME, DESCRIPTION, PRICE, IMGURL); 
		optionalProduct= Optional.of(new Product(ID, NAME, DESCRIPTION, PRICE, IMGURL) );
		
	}

	@Test
	void testFindAll() {
		when(repository.findAll()).thenReturn(List.of(product));
		
		List<Product>response=service.findAll();
		
		assertNotNull(response);
		assertEquals(1,response.size());
		assertEquals(Product.class,response.get(0).getClass());
		assertEquals(ID,response.get(0).getId());
		assertEquals(NAME,response.get(0).getName());
		assertEquals(DESCRIPTION,response.get(0).getDescription());
		assertEquals(PRICE,response.get(0).getPrice());
		assertEquals(IMGURL,response.get(0).getImgUrl());
	}

	@Test
	void testFindById() {
		
		when(repository.findById(anyLong())).thenReturn(optionalProduct);
		
		Product response=service.findById(ID);
		assertNotNull(response);
		assertEquals(Product.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(DESCRIPTION,response.getDescription());
		assertEquals(PRICE,response.getPrice());
		assertEquals(IMGURL,response.getImgUrl());

	
	
	}	

}
