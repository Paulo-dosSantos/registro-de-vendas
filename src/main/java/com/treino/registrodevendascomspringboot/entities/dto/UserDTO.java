package com.treino.registrodevendascomspringboot.entities.dto;
import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false,of= {"id"})
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	
	@NonNull
	private String name;
	
	@NonNull
	private String email;
	
	@NonNull
	private String phone;
}
