package com.gmaps.api.dto;


public class AuthResponseDTO {

	private String acessToken;
	private String tokenType = "Bearer ";
	
	public AuthResponseDTO(String acessToken) {
		this.acessToken = acessToken;
	}

	public AuthResponseDTO(String acessToken, String tokenType) {
		this.acessToken = acessToken;
		this.tokenType = tokenType;
	}

	public String getAcessToken() {
		return acessToken;
	}

	public void setAcessToken(String acessToken) {
		this.acessToken = acessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
		
	
}
