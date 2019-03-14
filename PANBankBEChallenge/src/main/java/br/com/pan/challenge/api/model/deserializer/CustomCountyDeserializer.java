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
import br.com.pan.challenge.api.model.County;

public class CustomCountyDeserializer extends StdDeserializer<List<County>> {

	/**
	 * First version
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger( CustomCountyDeserializer.class );

	public CustomCountyDeserializer() {
		this(null);
	}

	public CustomCountyDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public List<County> deserialize(JsonParser parser, DeserializationContext deserializer) {
		List<County> counties = null;
		ObjectCodec codec = parser.getCodec();
		JsonNode nodes = null;

		try {
			nodes = codec.readTree(parser);
			if (nodes != null && nodes.size() > 0) {
				counties = new ArrayList<>(nodes.size());
				extractCounties(nodes, counties);
			} else {
				counties = new ArrayList<>(1);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new JsonToJavaParsingException();
		}
		return counties;
	}

	private void extractCounties(JsonNode nodes, List<County> counties) {
		for (JsonNode node : nodes) {
			County county  = new County();
			county.setId(node.get("id").asLong());
			county.setName(node.get("nome").asText());
			county.setMicroRegion(node.get("microrregiao").get("nome").asText());
			counties.add(county);
		}
	}

}
