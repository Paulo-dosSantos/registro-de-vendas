package com.treino.registrodevendascomspringboot.entities.pk;

import java.io.Serializable;

import com.treino.registrodevendascomspringboot.entities.Order;
import com.treino.registrodevendascomspringboot.entities.Product;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class OrderItemPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Product product;
	
}
