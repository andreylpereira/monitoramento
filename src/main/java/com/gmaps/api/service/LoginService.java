package com.gmaps.api.service;


public interface LoginService {

	Boolean validLogin(String login);

	String formatLogin(String login);

	Boolean isCpf(String CPF);


}
