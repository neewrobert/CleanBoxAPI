package com.cleanbox.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "AguaInfo")
public class AguaInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -118021205086166439L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Double ph;
	
	private Double ntu;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPh() {
		return ph;
	}

	public void setPh(Double ph) {
		this.ph = ph;
	}

	public Double getNtu() {
		return ntu;
	}

	public void setNtu(Double ntu) {
		this.ntu = ntu;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	
	
	
	
	
	

}
