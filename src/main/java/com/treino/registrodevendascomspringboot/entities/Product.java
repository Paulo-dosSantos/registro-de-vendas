package com.treino.registrodevendascomspringboot.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tb_product")
@Data
@EqualsAndHashCode(callSuper = false, of= {"id"})
@NoArgsConstructor
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	
	@ManyToMany
	@JoinTable(name="tb_product_category",joinColumns = 
	@JoinColumn(name="product_id"),inverseJoinColumns = 
	@JoinColumn(name="category_id"))
	private Set<Category>categories= new HashSet<>();
	
	@OneToMany(mappedBy ="id.product")
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private Set<OrderItem>items=new HashSet<>();

	public Product(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	@JsonIgnore
	public Set<Order>getOrders(){
		Set<Order>set=new HashSet<>();
		for(OrderItem item: items) {
			set.add(item.getOrder());
		}
		return set;
	}
}
