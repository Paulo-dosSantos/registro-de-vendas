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

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<List<UserDTO>>findAll(){
		List<UserDTO>usersDTO=service.findAll()
				.stream().map(x-> mapper.map(x, UserDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(usersDTO);
	}
	@GetMapping(value="/{id}",produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<UserDTO>findById(@PathVariable Long id){
		User user= service.findById(id);
		return ResponseEntity.ok().body(mapper.map(user, UserDTO.class));
	}
	@PutMapping(value="/{id}",consumes= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML}
	,produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<UserDTO>update(@PathVariable Long id, @RequestBody UserDTO user){
		user.setId(id);
		User newUser= service.update(id,user);
		return ResponseEntity.ok().body(mapper.map(newUser, UserDTO.class));
	}
	@PostMapping(consumes= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML}
	,produces= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.APPLICATION_YML})
	public ResponseEntity<User>insert(@RequestBody User user){
		user= service.insert(user);
		URI uri= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
		
		/*URI uri= ServletUriComponentsBuilder.
				fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		*/
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<UserDTO>delete(@PathVariable Long id){	
		service.delete(id);
		return ResponseEntity.noContent().build();	
	}
}
