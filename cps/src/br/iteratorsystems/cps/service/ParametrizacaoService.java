package br.iteratorsystems.cps.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.iteratorsystems.cps.beans.ParametrizacaoBean;
import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ParametrizacaoDao;
import br.iteratorsystems.cps.entities.Tabelas_Parametrizacao;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ParametrizacaoService {

	ParametrizacaoDao parametrizacaoDao;
	private Session session;
	private Transaction transaction;
	
	public ParametrizacaoService() {
		session = HibernateConfig.getSession();
		parametrizacaoDao = new ParametrizacaoDao(Tabelas_Parametrizacao.class, session);
		
	}
	
	public Tabelas_Parametrizacao obterParametrizacao(final Integer parametrizacaoId) throws CpsDaoException{
		return parametrizacaoDao.obter(parametrizacaoId);
	}
	
	public void salvar(final Tabelas_Parametrizacao tabelasParametrizacao) throws CpsDaoException{
		transaction = session.beginTransaction();
		parametrizacaoDao.salvar(tabelasParametrizacao);
		transaction.commit();
	}

	public ParametrizacaoBean converteTabelaParametrizacaoEmParametrizacaoBean(final Tabelas_Parametrizacao parametrizacao) throws CpsDaoException {
		ParametrizacaoBean parametrizacaoBean = new ParametrizacaoBean();
		parametrizacaoBean.setDiretorioPadraoDeImagens(parametrizacao.getDiretorioImagensProCps());
		parametrizacaoBean.setDiretorioPadraoXML(parametrizacao.getDiretorioProcessamentoXml());
		parametrizacaoBean.setQuantidadeMaximaDeItens(Integer.parseInt(parametrizacao.getNumMaxItensLista()));
		parametrizacaoBean.setQuantidadeMaximaDeLojas(Integer.parseInt(parametrizacao.getNumMaxLojasComparacao()));
		return parametrizacaoBean;
	}
}
