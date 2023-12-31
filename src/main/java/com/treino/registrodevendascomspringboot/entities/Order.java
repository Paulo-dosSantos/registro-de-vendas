package com.treino.registrodevendascomspringboot.entities;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treino.registrodevendascomspringboot.entities.dto.UserDTO;
import com.treino.registrodevendascomspringboot.entities.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="tb_order")
@Data
@EqualsAndHashCode(callSuper = false, of= {"id"})
public class Order extends RepresentationModel<Order> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern= "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="GMT")
	@NonNull
	private Instant moment;
	
	@ManyToOne
	@JoinColumn(name="client_id")
	@Getter(AccessLevel.NONE)
	@NonNull
	private User client;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@OneToOne(mappedBy ="order",cascade=CascadeType.ALL)
	@NonNull
	private Payment payment;
	
	@OneToMany(mappedBy ="id.order",cascade=CascadeType.ALL)
	@Setter(AccessLevel.NONE)
	private Set<OrderItem>items=new HashSet<>();
	
	public Order( Instant moment, User client, OrderStatus orderStatus) {
		this.moment = moment;
		this.client = client;
		setOrderStatus(orderStatus);
	}
	
	public double getTotal() {
		double total=items.stream().map(x->x.getSubTotal()).reduce(0.0,(x,y)->x+y);
		return total;
	}
	public UserDTO getClient() {
		ModelMapper mapper=new ModelMapper();
		return mapper.map(client, UserDTO.class);
	}	
}
