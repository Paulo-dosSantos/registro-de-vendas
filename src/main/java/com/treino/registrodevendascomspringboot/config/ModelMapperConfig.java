package com.treino.registrodevendascomspringboot.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	

}
