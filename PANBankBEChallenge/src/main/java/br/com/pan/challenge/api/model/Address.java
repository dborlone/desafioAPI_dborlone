package br.com.pan.challenge.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
/*
 * Classe que representa um endereço nos padrões brasileiros... 
 * Criada em portugues somente a titulo de 
 * 
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
	
	private String zipCode;
	private String addressName;
	private String additionalAddressInformation;
	private String district;
	private City city;
	
}
