package br.com.pan.challenge.api.repository.builder;

import br.com.pan.challenge.api.model.Address;
import br.com.pan.challenge.api.model.City;
import br.com.pan.challenge.api.model.Country;
import br.com.pan.challenge.api.model.Customer;
import br.com.pan.challenge.api.model.State;
import br.com.pan.challenge.api.model.anums.Region;

public class CustomerBulder {
	
	private Customer instance;
	
	public CustomerBulder() {
		instance = new Customer();
	}
	
	public CustomerBulder inputCpf(String cpf) {
		instance.setCpf(cpf);
		return this;
	}
	
	public CustomerBulder inputName(String name) {
		instance.setName(name);
		return this;
	}
	
	public CustomerBulder mockAddress() {
		Address address = new Address();
		
		address.setZipCode("05864-180");
		address.setAddressName("Rua Remo Sarti");
		address.setAdditionalAddressInformation("Casa 2");
		address.setDistrict("Jardim Remo");
		
		City city = new City();
		city.setName("São Paulo");
		State state = new State();
		state.setId(11L);
		state.setName("São Paulo");
		state.setShortName("SP");
		state.setRegion(Region.SUDESTE);
		state.setCountry(new Country("Brasil"));
		city.setState(state);
		
		address.setCity(city);		
		instance.setAddress(address);
		
		return this;
	}
	
	public Customer build() {
		return this.instance;
	}
}
