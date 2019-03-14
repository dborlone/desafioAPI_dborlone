package br.com.pan.challenge.api.model.deserializer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import br.com.pan.challenge.api.exception.model.JsonToJavaParsingException;
import br.com.pan.challenge.api.model.Address;
import br.com.pan.challenge.api.model.City;
import br.com.pan.challenge.api.service.LocationService;

@Component
public class CustomAddressDeserializer extends StdDeserializer<Address> {

	private static final Logger logger = LoggerFactory.getLogger( CustomAddressDeserializer.class );
	
	private transient LocationService locationService;

	/**
	 * First version
	 */
	private static final long serialVersionUID = 1L;

	public CustomAddressDeserializer() {
		this(null);
	}

	public CustomAddressDeserializer(Class<Address> vc) {
		super(vc);
		locationService = new LocationService();
	}

	public CustomAddressDeserializer(LocationService locationService2, int i, int j) {
		this(null);
		this.locationService = locationService2;
		
		if(logger.isDebugEnabled()) {
			logger.debug(String.format("Custom constructor %d %d", i, j));
		}		
	}

	@Override
	public Address deserialize(JsonParser parser, DeserializationContext deserializer) {
		Address address = null;
		ObjectCodec codec = parser.getCodec();
		JsonNode nodes = null;

		try {
			nodes = codec.readTree(parser);
			if (nodes != null && nodes.size() > 0) {
				address = extractAddress(nodes);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new JsonToJavaParsingException();
		}
		return address;
	}

	private Address extractAddress(JsonNode node) {
		Address address = new Address();
		City city = new City();

		address.setZipCode(node.get("cep").asText());
		address.setAddressName(node.get("logradouro").asText());
		address.setAdditionalAddressInformation(node.get("complemento").asText());
		address.setDistrict(node.get("bairro").asText());
		address.setCity(city);
		
		city.setName(node.get("localidade").asText());
		city.setState(locationService.getStateByUf(node.get("uf").asText()));
		
		return address;
	}

}
