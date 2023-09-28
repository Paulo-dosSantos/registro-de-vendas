package com.treino.registrodevendascomspringboot.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.treino.registrodevendascomspringboot.entities.User;
import com.treino.registrodevendascomspringboot.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User user1=new User(null, "John Constantine",
				"johnzinho@gmail.com", "2194938932", "hellblazer");
			
			User user2=new User(null, "Bruce Wayne",
					"waynetech@gmail.com", "2194252545", "coringa_de_calcinha");

			User user3=new User(null, "Matthew Murdock",
					"mattmurdock@gmail.com", "2194255545", "hell's_kitchen");
			
			User user4=new User(null, "Erik Lansheer",
					"maguila@gmail.com", "2194552545", "magneto");
			
			User user5=new User(null, "Ororo Monroe",
					"ororo@gmail.com", "2194255245", "tempestade");
			
			userRepository.saveAll(Arrays.asList(user1,user2,user3,user4,user5));
		
	}

}
