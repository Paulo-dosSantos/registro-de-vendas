package com.treino.registrodevendascomspringboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.treino.registrodevendascomspringboot.entities.Category;
import com.treino.registrodevendascomspringboot.repositories.CategoryRepository;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class CategoryServiceTest {
	
	private static final String NAME = "Books";

	private static final long ID = 1L;

	private static final String OBJECT_NOT_FOUND = "Objeto não encontrado";

	@InjectMocks
	private CategoryService service;
	
	@Mock
	private CategoryRepository repository;
	
	private Category category;
	
	private Optional<Category>optionalCategory;
	
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		;
	}

	private  void startUser() {
		
		category=new Category(ID, NAME);
		optionalCategory=Optional.of(new Category(ID, NAME));
		
		
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void testFindAll() {
		when(repository.findAll()).thenReturn(List.of(category));
		
		List<Category>categories= service.findAll();
		
		assertNotNull(categories);
		assertEquals(1,categories.size());
		assertEquals(Category.class,categories.get(0).getClass());
		assertEquals(ID, categories.get(0).getId());
		assertEquals(NAME, categories.get(0).getName());
	}

	@Test
	void testFindById() {
		when(repository.findById(anyLong())).thenReturn(optionalCategory);
		
		Category response= service.findById(ID);
		
		assertNotNull(response);
		assertEquals(Category.class,response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
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
			
		}}

}
