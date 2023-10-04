package com.treino.registrodevendascomspringboot.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.treino.registrodevendascomspringboot.entities.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name="tb_order")
@Data
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Instant moment;
	
	@ManyToOne
	@JoinColumn(name="client_id")
	private User client;
	
	private Integer orderStatus;
	
	@OneToOne(mappedBy ="order",cascade=CascadeType.ALL)
	private Payment payment;
	
	@OneToMany(mappedBy ="id.order")
	private Set<OrderItem>items=new HashSet<>();
	
	public Order(Long id, Instant moment, User client, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.moment = moment;
		this.client = client;
		setOrderStatus(orderStatus);
	}
	public OrderStatus getOrderStatus() {
		return OrderStatus.valueOf(orderStatus);
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		if(orderStatus!=null) {
			this.orderStatus=orderStatus.getCode();
		}
	}
}
