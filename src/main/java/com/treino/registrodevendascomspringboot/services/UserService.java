package com.treino.registrodevendascomspringboot.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.repositories.UserRepository;

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
		
		return user.get();
	}
	public User update(UserDTO obj) {
		
		return userRepository.save(mapper.map(obj, User.class));
		
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	public User insert(UserDTO obj) {
		
		
		return userRepository.save(mapper.map(obj, User.class));
	}
	

}
