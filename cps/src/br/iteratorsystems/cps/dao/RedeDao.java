package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.entities.Tabelas_Rede;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class RedeDao extends DaoGeneric<Tabelas_Rede, Integer> {

	public RedeDao(Class<Tabelas_Rede> persistentClass, Session session) {
		super(persistentClass, session);
	}

	@SuppressWarnings("unchecked")
	public List<Tabelas_Rede> obterPorNome(String nomeRede) throws HibernateException, CpsDaoException {
		return getSession().createCriteria(Tabelas_Rede.class).add(Restrictions.eq("nome", nomeRede)).list();
	}

}
