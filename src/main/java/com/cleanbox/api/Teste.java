package com.cleanbox.api;

import java.util.regex.Pattern;

public class Teste {

	public static void main(String[] args) {
		
		
		String dados = "123.0 | -123.0";
		String dados1 = "-123.0 | 212.0";
		String dados2 = "123 | -212";
		String dados3 = "12311 | 212";
		String dados4 = "aaa";
		
		String[] split = dados1.split("\\|");
		
		
		
		
		String pattern = "\\D*\\d+\\D*\\d*\\s*\\|\\s*\\D*\\d+\\D*\\d*";
		
		
		
		System.out.println(dados.matches(pattern));
		System.out.println(dados1.matches(pattern));
		System.out.println(dados2.matches(pattern));
		System.out.println(dados3.matches(pattern));
		System.out.println(dados4.matches(pattern));
		
		
		
		
		
		
		
		
		

	}

}
