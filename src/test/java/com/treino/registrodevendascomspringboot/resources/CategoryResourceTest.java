package com.treino.registrodevendascomspringboot.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.treino.registrodevendascomspringboot.entities.Category;
import com.treino.registrodevendascomspringboot.services.CategoryService;

@SpringBootTest
class CategoryResourceTest {
	
	@InjectMocks
	private CategoryResource resource;
	
	@Mock
	private CategoryService service;
	
	private Category category;
	
	private static final String NAME = "Books";

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
		category=new Category( NAME);
		category.setId(1L);
	}

	@Test
	void testFindAll() {
		when(service.findAll()).thenReturn(List.of(category));
		
		ResponseEntity<List<Category>>response=resource.findAll();
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class,response.getClass());
		
		assertEquals(Category.class,response.getBody().get(0).getClass());
		assertEquals(ID,response.getBody().get(0).getId());
		assertEquals(NAME,response.getBody().get(0).getName());
	}

	@Test
	void testFindById() {
		when(service.findById(anyLong())).thenReturn(category);
		
		ResponseEntity<Category> response=resource.findById(ID);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(Category.class,response.getBody().getClass());
		assertEquals(1L,response.getBody().getId());
		assertEquals(NAME,response.getBody().getName());
	
	
	
	
	
	
	
	
	}

}
