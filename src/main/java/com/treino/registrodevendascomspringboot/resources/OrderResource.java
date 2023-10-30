package com.treino.registrodevendascomspringboot.resources;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treino.registrodevendascomspringboot.entities.Order;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.services.OrderService;
import com.treino.registrodevendascomspringboot.util.MediaType;
@RestController
@RequestMapping(value="/orders")
public class OrderResource {
	
	@Autowired
	private OrderService service;
	
	@GetMapping(produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<List<Order>>findAll(){
		List<Order>orders=service.findAll();
		orders.stream().forEach(x->x.add(linkTo(methodOn(OrderResource.class).findById(x.getId())).withSelfRel()));
		orders.stream().forEach(x->x.getClient().add(linkTo(methodOn(UserResource.class).findById(x.getClient().getId())).withSelfRel()));
		orders.stream().forEach(x->x.getItems().forEach(i->i.getProduct().add(linkTo(methodOn(ProductResource.class).findById(i.getProduct().getId())).withSelfRel())));
		orders.stream().forEach(x->x.getItems().forEach(i->i.getProduct().
			getCategories().stream().forEach(c->c.add(linkTo(methodOn(CategoryResource.class).findById(c.getId())).withSelfRel()))));
		return ResponseEntity.ok().body(orders);
	}
	@GetMapping(value="/{id}",produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<Order>findById(@PathVariable Long id){
		Order order= service.findById(id);
		order.getClient().add(linkTo(methodOn(UserResource.class).findById(order.getClient().getId())).withSelfRel());
		order.getItems().stream().forEach(x->x.getProduct().add(linkTo(methodOn(ProductResource.class).findById(x.getProduct().getId())).withSelfRel()));
		order.getItems().stream().forEach(x->x.getProduct().getCategories().stream().forEach(c->c.add(linkTo(methodOn(CategoryResource.class).findById(c.getId())).withSelfRel())));
		order.add(linkTo(methodOn(OrderResource.class).findAll()).withRel("Lista de pedidos:"));
		return ResponseEntity.ok().body(order);
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void>delete(@PathVariable Long id){	
		service.delete(id);
		return ResponseEntity.noContent().build();	
	}
}
