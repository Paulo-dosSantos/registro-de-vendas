package com.treino.registrodevendascomspringboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treino.registrodevendascomspringboot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User>findByEmail(String email);

}
