package com.treino.registrodevendascomspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treino.registrodevendascomspringboot.entities.Category;
import com.treino.registrodevendascomspringboot.repositories.CategoryRepository;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category>findAll(){
		return categoryRepository.findAll();
	}
	public Category findById(Long id) {
		
		Optional<Category> category=categoryRepository.findById(id);
		

		return category.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado") );
		
	}
	
}
