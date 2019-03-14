package br.com.pan.challenge.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pan.challenge.api.exception.model.ResourceNotFoundException;
import br.com.pan.challenge.api.model.Customer;
import br.com.pan.challenge.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

	@Autowired
	private CustomerRepository repository;

	public Customer getCustomerByCPF(String cpf) {
		Customer customerInMemoryToSave = repository.findByCPF(cpf);
		
		if(customerInMemoryToSave==null) {
			throw new ResourceNotFoundException();
		}	
		return customerInMemoryToSave;
	}

	public Customer updateCustomerAddress(String cpf, Customer customer) {
		Customer customerInMemoryToSave = repository.findByCPF(cpf);
		
		if(customerInMemoryToSave==null) {
			throw new ResourceNotFoundException();
		}		
		
		BeanUtils.copyProperties(customer, customerInMemoryToSave, "cpf", "name");
		repository.updateAddressByCpf(customerInMemoryToSave);
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Customer %s has been updated", cpf));
		}
		return customerInMemoryToSave;
	}

}
