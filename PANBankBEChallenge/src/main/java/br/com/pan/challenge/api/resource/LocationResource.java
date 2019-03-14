package br.com.pan.challenge.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pan.challenge.api.model.Address;
import br.com.pan.challenge.api.model.County;
import br.com.pan.challenge.api.model.State;
import br.com.pan.challenge.api.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationResource {

	@Autowired
	private LocationService locationService;

	@GetMapping("/street/{cep}")
	public ResponseEntity<Address> list(@PathVariable String cep) {
		Address adressList = locationService.getAddressByCep(cep);
		return  ResponseEntity.ok(adressList);
	}

	@GetMapping("/state")
	public ResponseEntity<List<State>> listStates() {
		List<State> states = locationService.listStates();
		return ResponseEntity.ok(states);
	}

	@GetMapping("/state/{stateId}/county")
	public ResponseEntity<List<County>> getStateCounty(@PathVariable Integer stateId) {
		List<County> states = locationService.listCounties(stateId);
		return ResponseEntity.ok(states);
	}

}
