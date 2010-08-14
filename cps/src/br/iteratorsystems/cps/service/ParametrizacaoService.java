package br.iteratorsystems.cps.service;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ParametrizacaoDao;
import br.iteratorsystems.cps.entities.Tabelas_Parametrizacao;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ParametrizacaoService {

	ParametrizacaoDao parametrizacaoDao;
	private Session session;
	
	public ParametrizacaoService() {
		session = HibernateConfig.getSession();
		parametrizacaoDao = new ParametrizacaoDao(Tabelas_Parametrizacao.class, session);
	}
	
	public Tabelas_Parametrizacao obterParametrizacao(final Integer parametrizacaoId) throws CpsDaoException{
		return parametrizacaoDao.obter(parametrizacaoId);
	}
	
	public void salvar(final Tabelas_Parametrizacao tabelasParametrizacao) throws CpsDaoException{
		parametrizacaoDao.salvar(tabelasParametrizacao);
	}
}
