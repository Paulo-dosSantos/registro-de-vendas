package com.treino.registrodevendascomspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treino.registrodevendascomspringboot.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	

}
