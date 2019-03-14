package br.com.pan.challenge.api.service.utils;

import java.util.List;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.pan.challenge.api.model.Address;
import br.com.pan.challenge.api.model.deserializer.CustomAddressDeserializer;
import br.com.pan.challenge.api.model.deserializer.CustomCountyDeserializer;
import br.com.pan.challenge.api.model.deserializer.CustomStateDeserializer;
import br.com.pan.challenge.api.service.LocationService;

public class ResteUtils {
	
	private ResteUtils() { // constructor not need		
	}

	public static ObjectMapper createOMToCustomStateDeserializer() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("CustomStateDeserializer", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(List.class, new CustomStateDeserializer());
		mapper.registerModule(module);
		
		return mapper;
	}
	
	public static ObjectMapper createOMToCustomCountyDeserializer() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("CustomCountyDeserializer", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(List.class, new CustomCountyDeserializer());
		mapper.registerModule(module);
		
		return mapper;
	}
	
	public static ObjectMapper createOMToCustomAddressDeserializer(LocationService locationService) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("CustomAddressDeserializer", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(Address.class, new CustomAddressDeserializer(locationService, 1, 2));
		mapper.registerModule(module);
		
		return mapper;
	}
	
}