package br.com.pan.challenge.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "pan.api.config")
@Data
public class PanApiProperty {
	
	private String allowedCorsOrigin;
	
	private String endpointState;
	private String endpointCounty;
	private String endpointCep;
}
