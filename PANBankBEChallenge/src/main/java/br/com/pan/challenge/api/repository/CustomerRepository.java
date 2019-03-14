package br.com.pan.challenge.api.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import br.com.pan.challenge.api.model.Customer;
import br.com.pan.challenge.api.repository.builder.CustomerBulder;

@Repository
public class CustomerRepository {

	private static Map<String, Customer> customerRepositoryInMemory = new ConcurrentHashMap<>();

	@PostConstruct
	public void loadDatabaseInMemory() {
		customerRepositoryInMemory.put("40255597819", new CustomerBulder().inputName("Danilo Romualdo Borlone")
																			 .inputCpf("40255597819")
																			 .mockAddress()
																			 .build());

		customerRepositoryInMemory.put("51755597819", new CustomerBulder().inputName("Maria Do Carmo Romualdo Borlone")
																			 .inputCpf("51755597819")
																			 .mockAddress()
																			 .build());
	}

	public Customer findByCPF(String cpf) {
		return customerRepositoryInMemory.get(cpf);
	}
	
	public void updateAddressByCpf(Customer customerToUpdate) {
		customerRepositoryInMemory.put(customerToUpdate.getCpf(), customerToUpdate);
	}
}
