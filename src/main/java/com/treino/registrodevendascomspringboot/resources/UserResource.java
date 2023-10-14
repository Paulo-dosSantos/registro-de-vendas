package com.treino.registrodevendascomspringboot.resources;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.services.UserService;
import com.treino.registrodevendascomspringboot.util.MediaType;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<List<UserDTO>>findAll(){
		List<User>users=service.findAll();
		List<UserDTO>usersDTO=users.stream().map(x->mapper.map(x, UserDTO.class)).collect(Collectors.toList());
		usersDTO.stream().forEach(x->x.add(linkTo(methodOn(UserResource.class).findById(x.getId())).withSelfRel()));
		return ResponseEntity.ok().body(usersDTO);
	}
	@GetMapping(value="/{id}",produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<UserDTO>findById(@PathVariable Long id){
		UserDTO userDTO= mapper.map(service.findById(id), UserDTO.class);
		userDTO.add(linkTo(methodOn(UserResource.class).findAll()).withRel("Lista de usuários: "));
		return ResponseEntity.ok().body(userDTO);
	}
	@PutMapping(value="/{id}",consumes= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML}
	,produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<UserDTO>update(@PathVariable Long id, @RequestBody UserDTO user){
		user.setId(id);
		UserDTO newUser= mapper.map(service.update(id,user), UserDTO.class );
		newUser.add(linkTo(methodOn(UserResource.class).findById(id)).withSelfRel());
		return ResponseEntity.ok().body(newUser);
	}
	@PostMapping(consumes= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML}
	,produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<UserDTO>insert(@RequestBody User user){
		UserDTO userDTO=mapper.map(service.insert(user), UserDTO.class) ;
		URI uri= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId()).toUri();
		userDTO.add(linkTo(methodOn(UserResource.class).findById(userDTO.getId())).withSelfRel());
		return ResponseEntity.created(uri).body(userDTO);
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<UserDTO>delete(@PathVariable Long id){	
		service.delete(id);
		return ResponseEntity.noContent().build();	
	}
}
