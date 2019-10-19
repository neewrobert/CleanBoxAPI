package com.cleanbox.api.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleanbox.api.model.AguaInfoModel;

@Service("aguaDao")
@Transactional
@Repository
public class AguaDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3552944828897958925L;

	@PersistenceContext
	private EntityManager em;

	private GenericDAO<AguaInfoModel> dao;

	@PostConstruct
	private void init() {

		this.dao = new GenericDAO<>(em, AguaInfoModel.class);

	}
	
	public void insert(AguaInfoModel t) {
		dao.insert(t);
	}

	public void remove(AguaInfoModel t) {
		dao.remove(t);
	}

	public void update(AguaInfoModel t) {
		dao.update(t);
	}

	public List<AguaInfoModel> getAll() {
		return dao.getAll();
	}

	public AguaInfoModel findById(long id) {
		return dao.findById(id);
	}
	
	public List<AguaInfoModel> buscaPorData(Date data){
		
		final Query query = em.createQuery(" SELECT a FROM AguaInfoModel as a WHERE a.data like '%:data'", AguaInfoModel.class);
		query.setParameter("data", data);
		
		return query.getResultList();
		
	}

	public List<AguaInfoModel> buscaPorPeriodo(Date dataInicial, Date dataFinal) {

		final Query query = em.createQuery(" SELECT a  FROM AguaInfoModel as a WHERE a.data BETWEEN :dataInicial AND :dataFinal ORDER BY a.data", AguaInfoModel.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		
		System.out.println("Buscando entre: " + dataInicial + " e " + dataFinal);
		
		return query.getResultList();
	}

}
