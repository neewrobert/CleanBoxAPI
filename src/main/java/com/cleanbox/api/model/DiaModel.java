package com.cleanbox.api.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DiaModel {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date data;

	private List<DetalheDiaModel> detalhesDia;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<DetalheDiaModel> getDetalhesDia() {
		return detalhesDia;
	}

	public void setDetalhesDia(List<DetalheDiaModel> detalhesDia) {
		this.detalhesDia = detalhesDia;
	}

	

}
