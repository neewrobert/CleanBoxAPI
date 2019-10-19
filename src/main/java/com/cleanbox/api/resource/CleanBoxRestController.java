package com.cleanbox.api.resource;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleanbox.api.business.AguaInfoBean;
import com.cleanbox.api.dao.AguaDAO;
import com.cleanbox.api.model.AguaInfoModel;
import com.cleanbox.api.model.DiaModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CleanBox")
@SpringBootApplication
@RestController
@RequestMapping("/aguainfo")
public class CleanBoxRestController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5502770656204155684L;

	@Autowired
	AguaDAO dao;

	@Autowired
	AguaInfoBean bean;


	@ApiOperation(value = "Busca entre duas datas")
	@RequestMapping(value = "/buscaporperiodo/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<DiaModel>> buscaPorPeriodo(
			@RequestParam("dataInicial") @DateTimeFormat(pattern="yyyy-MM-dd") Date dataInicial,
			@RequestParam("dataFinal") @DateTimeFormat(pattern="yyyy-MM-dd") Date dataFinal) {
		
		System.out.println("######### DATA INICIAL: " + dataInicial);
		
		if(dataInicial.after(dataFinal)) {
			return new ResponseEntity("Data inicial deve ser anterior a data final", HttpStatus.BAD_REQUEST);
		}
		
		LocalDate inicialLocalDate = bean.convertToLocalDate(dataInicial);
		LocalDate finalLocalDate = bean.convertToLocalDate(dataFinal);
		
		Period periodo = Period.between(inicialLocalDate, finalLocalDate);
		int dias = periodo.getDays();
		
		if(dias > 5 ) {
			return new ResponseEntity("O Periodo nao deve ser maior que 5 dias", HttpStatus.BAD_REQUEST);
		}
		
		List<DiaModel> diasPorPeriodo = bean.buscaPorPeriodo(dataInicial, dataFinal);
		System.out.println("Buscando por periodo");
		
		if (diasPorPeriodo == null || diasPorPeriodo.isEmpty()) {
			
			System.out.println("Dias nao encontrados");
			return new ResponseEntity("Nenhum valor encontrado", HttpStatus.NO_CONTENT);
		} else {
			
			System.out.println("######### DATA INICIAL: " + dataInicial);
			return new ResponseEntity<List<DiaModel>>(diasPorPeriodo, HttpStatus.OK);
		}

	}

	@ApiOperation(value = "Api para popular o banco com valore FAKES")
	@RequestMapping(value = "/populaBanco/", method = RequestMethod.POST)
	public ResponseEntity<String> populaBanco() {

		bean.populaBanco(5);
		return new ResponseEntity<String>(HttpStatus.CREATED);

	}

	@ApiOperation(value = "Cadastra um LOG")
	@RequestMapping(value = "/log/", method = RequestMethod.POST)
	public ResponseEntity<String> cadastraLaboratorio(@RequestBody AguaInfoModel model) {

		dao.insert(model);
		return new ResponseEntity<String>(HttpStatus.CREATED);

	}

}
