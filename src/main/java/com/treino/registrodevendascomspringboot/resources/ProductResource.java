package com.treino.registrodevendascomspringboot.resources;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treino.registrodevendascomspringboot.entities.Product;
import com.treino.registrodevendascomspringboot.services.ProductService;
import com.treino.registrodevendascomspringboot.util.MediaType;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping(value="/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping(produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<List<Product>>findAll(){
		List<Product>products=service.findAll();
		products.stream().forEach(x->x.getCategories().forEach(c->c.add(linkTo(methodOn(CategoryResource.class).findById(c.getId())).withSelfRel())));
		products.forEach(x->x.add(linkTo(methodOn(ProductResource.class).findById(x.getId())).withSelfRel()));
			
		return ResponseEntity.ok().body(products);
	}
	@GetMapping(value="/{id}",produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<Product>findById(@PathVariable Long id){
		Product product= service.findById(id);
		product.getCategories().stream().forEach(x->x.add(linkTo(methodOn(CategoryResource.class).findById(x.getId())).withSelfRel()));
		
		product.add(linkTo(methodOn(ProductResource.class).findAll()).withRel("Lista de produtos: "));
		return ResponseEntity.ok().body(product);
	}
}
