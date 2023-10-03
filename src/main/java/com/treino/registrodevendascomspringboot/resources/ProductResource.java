package com.treino.registrodevendascomspringboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treino.registrodevendascomspringboot.entities.Category;
import com.treino.registrodevendascomspringboot.entities.Product;
import com.treino.registrodevendascomspringboot.services.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	
	
	@GetMapping
	public ResponseEntity<List<Product>>findAll(){
		List<Product>products=service.findAll();
		
		return ResponseEntity.ok().body(products);
	}
	@GetMapping(value="/{id}")
	public ResponseEntity<Product>findById(@PathVariable Long id){
		Product product= service.findById(id);
		
		return ResponseEntity.ok().body(product);
	}
}
