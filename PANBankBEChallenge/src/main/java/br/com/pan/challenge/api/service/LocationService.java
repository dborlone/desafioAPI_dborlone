package br.com.pan.challenge.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pan.challenge.api.config.PanApiProperty;
import br.com.pan.challenge.api.exception.model.JsonToJavaParsingException;
import br.com.pan.challenge.api.exception.model.ResourceNotFoundException;
import br.com.pan.challenge.api.model.Address;
import br.com.pan.challenge.api.model.County;
import br.com.pan.challenge.api.model.State;
import br.com.pan.challenge.api.service.utils.ResteUtils;

@Service
@CacheConfig(cacheNames = { "allStatesCached" })
public class LocationService {

	@Autowired
	private PanApiProperty panApiProperty;
	
	private static final Logger logger = LoggerFactory.getLogger( LocationService.class );
	
	/*
	 * Esse cache foi criado manual somente para demonstração de conhecimento de técnicas
	 */
	private static final Map<String, State> MANUAL_STATE_CACHE_JUST_TO_SHOW_OTHER_WAY = new ConcurrentHashMap<>();
	
	private void updateManualCacheForCEP(List<State> statesList) {
		statesList.forEach(state ->	MANUAL_STATE_CACHE_JUST_TO_SHOW_OTHER_WAY.put(state.getShortName(), state));
	}
	
	private String callRestWebService(String urlToCall) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(urlToCall, String.class);
	}	

	private StringBuilder createCountyEndpoint(Integer stateId) {
		StringBuilder apiEndpoint = new StringBuilder(panApiProperty.getEndpointCounty());
		
		int i = apiEndpoint.indexOf("{id}");
		apiEndpoint.delete(i, i+4)
				   .insert(i, stateId);
		return apiEndpoint;
	}
	
	private StringBuilder createCepEndpoint(String cep) {
		StringBuilder apiEndpoint = new StringBuilder(panApiProperty.getEndpointCep());
		
		int i = apiEndpoint.indexOf("{cep}");
		apiEndpoint.delete(i, i+5)
				   .insert(i, cep);
		
		return apiEndpoint;
	}
	
	/*
	 * Método que contem um cache manual, apenas por demonstração
	 */
	public State getStateByUf(String uf) {
		if(MANUAL_STATE_CACHE_JUST_TO_SHOW_OTHER_WAY.isEmpty()) {
			listStates();
		}
		return MANUAL_STATE_CACHE_JUST_TO_SHOW_OTHER_WAY.get(uf);		
	}
	
	/*
	 * Método que contem um cache automático do Spring, apenas por demonstração
	 */
	@SuppressWarnings("unchecked")
	@Cacheable
	public List<State> listStates() {
		List<State> statesList =  null;		
		String json = callRestWebService(panApiProperty.getEndpointState());		
		ObjectMapper mapper = ResteUtils.createOMToCustomStateDeserializer();
		
		try {
			statesList = (List<State>) mapper.readValue(json, List.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new JsonToJavaParsingException();
		}

		if (!statesList.isEmpty()) {
			Collections.sort(statesList, new State.StateComparator());
			updateManualCacheForCEP(statesList);
		}		
		
		return statesList;
	}
	
	@SuppressWarnings("unchecked")
	@Cacheable(value = "counties")
	public List<County> listCounties(Integer stateId) {
		List<County> countyList = new ArrayList<>(1);
		StringBuilder apiEndpoint = createCountyEndpoint(stateId);		
		String json = callRestWebService(apiEndpoint.toString());		
		ObjectMapper mapper = ResteUtils.createOMToCustomCountyDeserializer();

		try {
			countyList = (List<County>) mapper.readValue(json, List.class);
			
			if(countyList.isEmpty()) {
				throw new ResourceNotFoundException();
			}			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new JsonToJavaParsingException();
		}
		
		return countyList;
	}


	public Address getAddressByCep(String cep) {
		Address address = null;
		StringBuilder apiEndpoint = createCepEndpoint(cep);
		ObjectMapper mapper = ResteUtils.createOMToCustomAddressDeserializer(this);
		try {
			String json = callRestWebService(apiEndpoint.toString());
			if(json.contains("erro")) {
				throw new ResourceNotFoundException();
			}		
			address = mapper.readValue(json, Address.class);		
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new JsonToJavaParsingException();
		}
		
		return address;
	}


}
