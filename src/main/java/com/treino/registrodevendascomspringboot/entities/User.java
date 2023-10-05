package com.treino.registrodevendascomspringboot.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false, of= {"id"})
@NoArgsConstructor
@Table(name="tb_user")
public class User implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(unique=true)
	private String email;
	
	@Column(length = 10)
	private String phone;
	private String password;
	
	
	@JsonIgnore
	@OneToMany(mappedBy="client")
	private List<Order> orders=new ArrayList<>();


	public User(Long id, String name, String email, String phone, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}
	
	

}
