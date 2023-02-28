package com.gmaps.api.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "users")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "O campo login esta em branco.")
	@Size(min = 11, max = 11, message = "Digite um CPF válido.")
	@NotNull
	private String login;

	@NotEmpty(message = "O campo senha esta em branco.")
	@NotNull
	private String password;
	
	@NotEmpty(message = "O campo nome esta em branco.")
	@Size(min = 3, max = 50, message = "O campo nome deve ter entre 3 a 50 caracteres.")
	@NotNull
	private String name;

	@Email(message = "O e-mail digita é inválido.")
	@NotEmpty(message = "O campo e-mail esta em branco.")
	@NotNull
	private String email;
	
	@NotEmpty(message = "O campo cidade esta em branco.")
	@Size(min = 3, max = 25, message = "O campo cidade deve ter entre 3 a 25 caracteres.")
	@NotNull
	private String city;
	
	@Size(max = 13, message = "O campo telefone deve ter no maximo 13 caracteres.")
	private String phone;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@JsonBackReference
	private List<Role> roles = new ArrayList<>();
	
	public UserEntity() {
		
	}

	public UserEntity(int id,
			@NotEmpty(message = "O campo login esta em branco.") @Size(min = 11, max = 11, message = "Digite um CPF válido.") @NotNull String login,
			@NotEmpty(message = "O campo senha esta em branco.") @NotNull String password,
			@NotEmpty(message = "O campo nome esta em branco.") @Size(min = 3, max = 50, message = "O campo nome deve ter entre 3 a 50 caracteres.") @NotNull String name,
			@Email(message = "O e-mail digita é inválido.") @NotEmpty(message = "O campo e-mail esta em branco.") @NotNull String email,
			@NotEmpty(message = "O campo cidade esta em branco.") @Size(min = 3, max = 25, message = "O campo cidade deve ter entre 3 a 25 caracteres.") @NotNull String city,
			@Size(max = 13, message = "O campo telefone deve ter no maximo 13 caracteres.") String phone,
			List<Role> roles) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.email = email;
		this.city = city;
		this.phone = phone;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	

}
