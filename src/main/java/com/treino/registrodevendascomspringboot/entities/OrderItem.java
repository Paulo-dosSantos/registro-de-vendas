package com.treino.registrodevendascomspringboot.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treino.registrodevendascomspringboot.entities.pk.OrderItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name="tb_order_item")
public class OrderItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrderItemPK id=new OrderItemPK();

	@Getter
	@Setter
	private Integer quantity;
	
	@Getter
	@Setter
	private Double price;
	
	public OrderItem(Order order, Product product
			, Integer quantity, Double price) {
		
		id.setOrder(order);
		id.setProduct(product);
		this.quantity=quantity;
		this.price=price;
	}
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}
	public void setOrder(Order order) {
		id.setOrder(order);
	}
	public Product getProduct() {
		return id.getProduct();
	}
	public void setProduct(Product product) {
		id.setProduct(product);
	}

}
