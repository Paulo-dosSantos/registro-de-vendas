package com.treino.registrodevendascomspringboot.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tb_product")
@Data
@EqualsAndHashCode(callSuper = false, of= {"id"})
@NoArgsConstructor
@RequiredArgsConstructor
public class Product extends RepresentationModel<Product> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private String name;
	
	@NonNull
	private String description;
	
	@NonNull
	private Double price;
	
	@NonNull
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

	@JsonIgnore
	public Set<Order>getOrders(){
		Set<Order>set=new HashSet<>();
		for(OrderItem item: items) {
			set.add(item.getOrder());
		}
		return set;
	}
}
