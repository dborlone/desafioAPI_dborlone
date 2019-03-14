package br.com.pan.challenge.api.model.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import br.com.pan.challenge.api.exception.model.JsonToJavaParsingException;
import br.com.pan.challenge.api.model.Country;
import br.com.pan.challenge.api.model.State;
import br.com.pan.challenge.api.model.anums.Region;

public class CustomStateDeserializer extends StdDeserializer<List<State>> {
	/**
	 * First version
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger( CustomStateDeserializer.class );
	
	/*
	 * Criado um pais padrao somente a titulo de demonstracao de conhecimento de recursos...
	 */
	private static final Country DEFAULT_COUNTRY = new Country("Brasil");


	public CustomStateDeserializer() {
		this(null);
	}

	public CustomStateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public List<State> deserialize(JsonParser parser, DeserializationContext deserializer) {
		List<State> states = null;
		ObjectCodec codec = parser.getCodec();
		JsonNode nodes = null;

		try {
			nodes = codec.readTree(parser);
			if (nodes != null && nodes.size() > 0) {
				states = new ArrayList<>(nodes.size());
				extractStates(nodes, states);
			} else {
				states = new ArrayList<>(1);
			}
		}  catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new JsonToJavaParsingException();
		}
		return states;
	}

	private void extractStates(JsonNode nodes, List<State> states) {
		for (JsonNode node : nodes) {
			State state  = new State();
			state.setId(node.get("id").asLong());
			state.setShortName(node.get("sigla").asText());
			state.setName(node.get("nome").asText());
			state.setRegion(Region.fromValue(node.get("regiao").get("nome").asText()));
			state.setCountry(DEFAULT_COUNTRY);
			states.add(state);
		}
	}

}
