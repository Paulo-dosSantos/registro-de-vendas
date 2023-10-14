package com.treino.registrodevendascomspringboot.resources;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treino.registrodevendascomspringboot.entities.Category;
import com.treino.registrodevendascomspringboot.services.CategoryService;
import com.treino.registrodevendascomspringboot.util.MediaType;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping(value="/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping(produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<List<Category>>findAll(){
		List<Category>categories=service.findAll();
		categories.stream().forEach(x->x.add(linkTo(methodOn(CategoryResource.class).findById(x.getId())).withSelfRel()));
		return ResponseEntity.ok().body(categories);
	}
	@GetMapping(value="/{id}",produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<Category>findById(@PathVariable Long id){
		Category category= service.findById(id);	
		category.add(linkTo(methodOn(CategoryResource.class).findAll()).withRel("Lista de categorias: "));
		return ResponseEntity.ok().body(category);
	}
}
