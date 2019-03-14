package br.com.pan.challenge.api.exception.model;

import lombok.Getter;
import lombok.ToString;

@ToString(of="code")
public enum OperationalResult {	
	
	SUCCESS("0"),
	GENERIC_ERROR("-1"),
	GENERIC_WARN("1"),	
	NOT_FOUND("-2"),
	CHILD_NOT_FOUND("-3");
	
	@Getter 
	private String code;
	
	OperationalResult(String code) {
		this.code = code;
	}
	
}
