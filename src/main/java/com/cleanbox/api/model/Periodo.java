package com.cleanbox.api.model;

public enum Periodo {
	
	MANHA("manha"),
	TARDE("tarde"),
	NOITE("noite");
	
	
	public String valorPeriodo;
	
	Periodo(String valor) {
        valorPeriodo = valor;
    }
	
	

}
