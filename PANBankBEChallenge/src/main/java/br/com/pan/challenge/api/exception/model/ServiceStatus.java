package br.com.pan.challenge.api.exception.model;

import lombok.Data;

@Data
public class ServiceStatus {
	
	private OperationalResult code;
	private String userMessage;
	private String supportMessage;
	
	public ServiceStatus() {
		super();
	}
		
	public ServiceStatus(String userMessage, String supportMessage) {
		super();
		this.setCode(OperationalResult.GENERIC_ERROR);
		this.userMessage = userMessage;
		this.supportMessage = supportMessage;
	}
	
	public ServiceStatus(OperationalResult operationResult, String userMessage, String supportMessage) {
		super();
		this.code = operationResult;
		this.userMessage = userMessage;
		this.supportMessage = supportMessage;
	}
}
