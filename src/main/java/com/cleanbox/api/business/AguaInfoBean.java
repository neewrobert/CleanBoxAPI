package com.cleanbox.api.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleanbox.api.dao.AguaDAO;
import com.cleanbox.api.model.AguaInfoModel;
import com.cleanbox.api.model.DetalheDiaModel;
import com.cleanbox.api.model.DiaModel;
import com.cleanbox.api.model.Periodo;

@Service
public class AguaInfoBean {
	
	@Autowired
	AguaDAO aguaDao;

	public void populaBanco(int dias) {
		
		int cont = 0;
		Calendar cal = Calendar.getInstance();

		for (int j = dias ; j >= 0 ; j--) {
			
			cal.add(Calendar.DAY_OF_MONTH, j * -1);
		
			System.out.println("INSERINDO DIA " + cal.getTime());
			
			for (int i = 5; i <= 13; i++) {

				AguaInfoModel model = new AguaInfoModel();
				Random random = new Random();

				model.setPh(random.nextDouble() * 14);
				model.setNtu(Double.valueOf(random.nextInt((400 - 150) + 1) + 150));
				

				cal.set(Calendar.HOUR_OF_DAY, i);
				model.setData(cal.getTime());
				aguaDao.insert(model);
				
				cont++;

			}

			for (int i = 13; i <= 19; i++) {

				AguaInfoModel model = new AguaInfoModel();
				Random random = new Random();

				model.setPh(random.nextDouble() * 14);
				model.setNtu(Double.valueOf(random.nextInt((400 - 150) + 1) + 150));

				cal.set(Calendar.HOUR_OF_DAY, i);
				model.setData(cal.getTime());
				aguaDao.insert(model);
				
				cont++;

			}

			for (int i = 20; i <= 23; i++) {

				AguaInfoModel model = new AguaInfoModel();
				Random random = new Random();

				model.setPh(random.nextDouble() * 14);
				model.setNtu(Double.valueOf(random.nextInt((400 - 150) + 1) + 150));

				cal.set(Calendar.HOUR_OF_DAY, i);
				model.setData(cal.getTime());
				aguaDao.insert(model);
				cont++;

			}
			
			cal = Calendar.getInstance();
			
		}
		
		System.out.println("################## FORAM INSERIDOS: " + cont);

	}

	public void gravaMsgMQTT(String message) {

		System.out.println("Entrando no Bean");
		AguaInfoModel model = new AguaInfoModel();
		System.out.println(message);

		String dadosSlipted[] = message.split("\\|");

		System.out.println("DADOS SPLITED 0: " + dadosSlipted[0]);
		System.out.println("DADOS SPLITED 1: " + dadosSlipted[1]);

		model.setPh(Double.valueOf(dadosSlipted[0]));
		model.setNtu(Double.valueOf(dadosSlipted[1]));

		Calendar cal = Calendar.getInstance();
		model.setData(cal.getTime());
		System.out.println("model: " + model);
		System.out.println("Agua DAO: " + aguaDao.toString());

		aguaDao.insert(model);

	}

	private List<AguaInfoModel> buscaInfoPorPeriodo(Periodo periodo, Date data) {

		List<AguaInfoModel> listaDeInfos;

		listaDeInfos = aguaDao.buscaPorPeriodo(builDataInicialPorPeriodo(data, periodo),
				builDataFinalPorPeriodo(data, periodo));
		
		System.out.println("CLASS DAO - Infos encontradas: " + listaDeInfos.size());
		

		return listaDeInfos;

	}

	private DetalheDiaModel calculaMediaPorPeriodo(List<AguaInfoModel> listaDeInfos, Periodo periodo) {
		
		
		if(listaDeInfos == null || listaDeInfos.isEmpty()) {
			return null;
		}
		Double somaPh = new Double(0);
		Double somaNtu = new Double(0);
		int size = listaDeInfos.size();

		for (AguaInfoModel model : listaDeInfos) {

			somaPh += model.getPh();
			somaNtu += model.getNtu();

		}

		DetalheDiaModel detalheDiaModel = new DetalheDiaModel();
		detalheDiaModel.setPeriodo(periodo);
		detalheDiaModel.setNtu(somaNtu / size);
		detalheDiaModel.setPh(somaPh / size);

		return detalheDiaModel;
	}

	public List<DiaModel> buscaPorPeriodo(Date dataInical, Date dataFinal) {
		
		System.out.println("BEAN - Iniciando busca por periodos");

		LocalDate inicialLocalDate = convertToLocalDate(dataInical);
		LocalDate finalLocalDate = convertToLocalDate(dataFinal);
		// calcula os dias entre as datas
		Period periodo = Period.between(inicialLocalDate, finalLocalDate);
		int dias = periodo.getDays();

		// Cria uma lista de Dias
		ArrayList<DiaModel> diasList = new ArrayList<>();

		// para cada dia, busca o periodo MANHA, TARDE, NOITE
		for (int i = 0; i <= dias; i++) {

			DetalheDiaModel detalheDiaModelManha = calculaMediaPorPeriodo(
					buscaInfoPorPeriodo(Periodo.MANHA, dataInical), Periodo.MANHA);
			DetalheDiaModel detalheDiaModelTarde = calculaMediaPorPeriodo(
					buscaInfoPorPeriodo(Periodo.TARDE, dataInical), Periodo.TARDE);
			DetalheDiaModel detalheDiaModelNoite = calculaMediaPorPeriodo(
					buscaInfoPorPeriodo(Periodo.NOITE, dataInical), Periodo.NOITE);
			
			

			ArrayList<DetalheDiaModel> detalhesDoDia = new ArrayList<>();
			int periodosEncontrados = 0;
			
			if(detalheDiaModelManha != null) {
				detalhesDoDia.add(detalheDiaModelManha);
				periodosEncontrados++;
			}
			
			if(detalheDiaModelTarde != null) {
				detalhesDoDia.add(detalheDiaModelTarde);
				periodosEncontrados++;
			}
			if(detalheDiaModelNoite != null) {
				detalhesDoDia.add(detalheDiaModelNoite);
				periodosEncontrados++;
			}
			
			if(periodosEncontrados > 0) {
				DiaModel dia = new DiaModel();
				dia.setData(dataInical);
				dia.setDetalhesDia(detalhesDoDia);

				diasList.add(dia);
			}
			

			
			dataInical = convertToDate(inicialLocalDate.plusDays(1));
			inicialLocalDate = inicialLocalDate.plusDays(1);
			System.out.println("################ Buscando agora pelo dia: " + dataInical);

		}

		return diasList;

	}


	private Date builDataInicialPorPeriodo(Date data, Periodo periodo) {
		
		Calendar cal = Calendar.getInstance();

		switch (periodo) {
		case MANHA:

			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 5);
			
			return cal.getTime();

		case TARDE:
			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 13);
			
			return cal.getTime();

		case NOITE:
			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 20);
			
			return cal.getTime();
		}
		return cal.getTime();

	}

	private Date builDataFinalPorPeriodo(Date data, Periodo periodo) {

		Calendar cal = Calendar.getInstance();
		switch (periodo) {
		case MANHA:
			
			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 13);
			
			return cal.getTime();

		case TARDE:
			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 19);
			
			return cal.getTime();

		case NOITE:
			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			
			return cal.getTime();
		}
		return cal.getTime();
	}
	
	
	public LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.of("America/Sao_Paulo"))
	      .toLocalDate();
	}
	
	public Date convertToDate(LocalDate dateToConvert) {
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.of("America/Sao_Paulo"))
	      .toInstant());
	}

}
