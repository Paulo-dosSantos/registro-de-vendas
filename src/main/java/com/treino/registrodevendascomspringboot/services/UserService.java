package com.treino.registrodevendascomspringboot.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.repositories.UserRepository;
import com.treino.registrodevendascomspringboot.services.exceptions.DataException;
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
		User user= userRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado"));
		return user;
	}
	public User update(Long id,UserDTO obj) {
		User objUser=mapper.map(obj, User.class);
		analyzeNumber(objUser);
		findByEmail(objUser);
		User entity= userRepository.getReferenceById(id);
		updateData(entity,objUser);
		return userRepository.save(entity);
	}
	
	private void updateData(User entity, User objUser) {
		entity.setName(objUser.getName());
		entity.setEmail(objUser.getEmail());
		entity.setPassword(objUser.getPassword());
		
	}
	private void findByEmail(User obj) {
		Optional<User>user=userRepository.findByEmail(obj.getEmail());
		if(user.isPresent()&& !(user.get().getId().equals(obj.getId()))) {
			 throw new DataIntegrityViolationException("Este e-mail já está cadastrado");		}
	}
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	public User insert(User obj) {
		analyzeNumber(obj);
		findByEmail(obj);
		User savedUser= userRepository.save(obj);
		return savedUser;
	}
	public void analyzeNumber(User obj) { 
		String number=obj.getPhone();
		if(number.length()>10) {
			throw new DataException("o número de telefone deve ter até 10 caracteres");
		}
	}
}
