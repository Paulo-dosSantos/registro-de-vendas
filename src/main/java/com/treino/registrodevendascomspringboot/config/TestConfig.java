package com.treino.registrodevendascomspringboot.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.treino.registrodevendascomspringboot.entities.Order;
import com.treino.registrodevendascomspringboot.entities.Payment;
import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.entities.enums.OrderStatus;
import com.treino.registrodevendascomspringboot.repositories.OrderRepository;
import com.treino.registrodevendascomspringboot.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void run(String... args) throws Exception {
		User u1=new User(null, "John Constantine",
				"johnzinho@gmail.com", "2194938932", "hellblazer");
			
			User u2=new User(null, "Bruce Wayne",
					"waynetech@gmail.com", "2194252545", "coringa_de_calcinha");

			
			
			
			Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), u1,OrderStatus.PAID);
			Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), u2,OrderStatus.WAITING_PAYMENT);
			Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), u1, OrderStatus.WAITING_PAYMENT); 
			
			userRepository.saveAll(Arrays.asList(u1,u2));
			orderRepository.saveAll(Arrays.asList(o1,o2,o3));
			
			Payment pay1= new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
			o1.setPayment(pay1);
			orderRepository.save(o1);
	}

}
