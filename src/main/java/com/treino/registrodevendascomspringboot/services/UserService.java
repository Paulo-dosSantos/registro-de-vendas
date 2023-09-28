package com.treino.registrodevendascomspringboot.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.repositories.UserRepository;
import com.treino.registrodevendascomspringboot.services.exceptions.DataIntegrityViolationException;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public List<User>findAll(){
		return userRepository.findAll();
	}
	public User findById(Long id) {
		
		Optional<User>user=userRepository.findById(id);
		
		return user.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado"));
		
	}
	public User update(UserDTO obj) {
		findByEmail(obj);
		return userRepository.save(mapper.map(obj, User.class));
		
	}
	
	private void findByEmail(UserDTO obj) {
		
		Optional<User>user=userRepository.findByEmail(obj.getEmail());
		
		if(user.isPresent()) {
			 throw new DataIntegrityViolationException("Este e-mail já está cadastrado")
;		}
		
	}
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	public User insert(UserDTO obj) {
		
		
		return userRepository.save(mapper.map(obj, User.class));
	}
	

}
