package com.treino.registrodevendascomspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treino.registrodevendascomspringboot.entities.Order;
import com.treino.registrodevendascomspringboot.repositories.OrderRepository;
import com.treino.registrodevendascomspringboot.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order>findAll(){
		return orderRepository.findAll();
	}
	public Order findById(Long id) {
		
		Optional<Order> order=orderRepository.findById(id);
		

		return order.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado") );
		
	}
	
}
