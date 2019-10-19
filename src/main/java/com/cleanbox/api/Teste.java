package com.cleanbox.api;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		
		
		LocalDate dataFinal = LocalDate.now();
		LocalDate dataInicial = LocalDate.of(2019, 10, 10);
		
		List<LocalDate> periodos = new ArrayList<>();
		Period periodo = Period.between(dataInicial, dataFinal);
		int dias = periodo.getDays();
		
		
		System.out.println(dias);

	}

}
