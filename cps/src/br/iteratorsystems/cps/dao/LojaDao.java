package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.entities.Tabelas_Loja;
import br.iteratorsystems.cps.entities.Tabelas_LojaId;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class LojaDao extends DaoGeneric<Tabelas_Loja, Tabelas_LojaId> {

	public LojaDao(Class<Tabelas_Loja> persistentClass, Session session) {
		super(persistentClass, session);
	}

	@SuppressWarnings("unchecked")
	public List<Tabelas_Loja> obterLojaPorNomeFantasia(String nomefantasia) throws HibernateException, CpsDaoException {
		return getSession().createCriteria(Tabelas_Loja.class).add(Restrictions.eq("nomefantasia", nomefantasia)).list();
	}

}
