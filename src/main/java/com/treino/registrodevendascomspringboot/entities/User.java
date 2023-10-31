package com.treino.registrodevendascomspringboot.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@Entity
@Data
@EqualsAndHashCode(callSuper = false, of= {"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="tb_user")
public class User  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private String name;
	
	@NonNull
	@Column(unique=true)
	private String email;
	
	@NonNull
	@Column(length = 10)
	private String phone;
	
	@NonNull
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy="client",cascade=CascadeType.ALL)
	private List<Order> orders=new ArrayList<>();
}
