package br.com.pan.challenge.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class Customer {
	
	@NotNull
	@NotBlank
	@CPF
	private String cpf;
	
	private String name;
	
	@NotNull
	private Address address;
	
}
