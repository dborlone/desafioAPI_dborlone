package br.com.pan.challenge.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pan.challenge.api.model.Customer;
import br.com.pan.challenge.api.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/{cpf}")
	public ResponseEntity<Customer> getByCPF(@PathVariable String cpf) {
		Customer customer = customerService.getCustomerByCPF(cpf);
		return  ResponseEntity.ok(customer);
	}

	@PutMapping("/{cpf}")
	public ResponseEntity<Customer> listStates(@PathVariable String cpf,  @Valid @RequestBody Customer customer) {
		Customer saved = customerService.updateCustomerAddress(cpf, customer);
		return  ResponseEntity.ok(saved);
	}
}
