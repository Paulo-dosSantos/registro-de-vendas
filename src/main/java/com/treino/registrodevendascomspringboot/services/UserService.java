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
		Optional<User>user=userRepository.findById(id);
		return user.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado"));
	}
	public User update(Long id,UserDTO obj) {
		obj.setId(id);
		User objUser=mapper.map(obj, User.class);
		analyzeNumber(objUser);
		findByEmail(objUser);
		analyzeNumber(objUser);
		return userRepository.save(objUser);
	}
	private void findByEmail(User obj) {
		Optional<User>user=userRepository.findByEmail(obj.getEmail());
		User entity= userRepository.getReferenceById(obj.getId());
		if(user.isPresent()&& !user.get().getId().equals(entity.getId())) {
			 throw new DataIntegrityViolationException("Este e-mail já está cadastrado");		}
	}
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	public User insert(User obj) {
		analyzeNumber(obj);
		findByEmail(obj);
		
		return userRepository.save(obj);
	}
	public void analyzeNumber(User obj) { 
		String number=obj.getPhone();
		if(number.length()>10) {
			throw new DataException("o número de telefone deve ter até 10 caracteres");
		}
	}
}
