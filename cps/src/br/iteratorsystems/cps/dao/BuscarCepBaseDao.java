package br.iteratorsystems.cps.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Cep;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class BuscarCepBaseDao {

	private static final Log log = LogFactory.getLog(BuscarCepBaseDao.class);
	
	public Tabelas_Cep buscarCep(final String cep) throws CpsDaoException {
		log.debug("buscando cep no dao "+cep);
		Tabelas_Cep cepObj = null;
		final String queryBusca = montaQueryBusca(cep);
		Query query = null;
		try{
			query =	HibernateConfig.getSession().createQuery(queryBusca);
			query.setParameter("cep",cep);
			cepObj = (Tabelas_Cep) query.uniqueResult();
		}catch (HibernateException e) {
			log.error("erro ao buscar o cep "+e);
			throw new CpsDaoException(e);
		}
		return converteObjetoCep(cepObj);
	}
	
	private Tabelas_Cep converteObjetoCep(Tabelas_Cep cep) {
		return cep;
	}
	
	private String montaQueryBusca(String cep) {
		StringBuilder builder = new StringBuilder();
		builder.append("select ");
		builder.append("c.logradouro,");
		builder.append("b.bairro,");
		builder.append("l.localidade,");
		builder.append("c.uf ");
		builder.append("from Tabelas_Cep as c,");
		builder.append("Tabelas_Bairro as b,");
		builder.append("Tabelas_Localidade as l ");
		builder.append("where c.cep = :cep ");
		builder.append("and ");
		builder.append("l.idLocalidade = c.localidade.idLocalidade ");
		builder.append("and ");
		builder.append("b.id.idBairro = c.bairro1 ");
		return builder.toString();
	}
	
	public static void main(String[] args) {
		BuscarCepBaseDao dao = new BuscarCepBaseDao();
		try {
			Tabelas_Cep cep =
			dao.buscarCep("06753160");
			System.out.println(cep);
		} catch (CpsDaoException e) {
			e.printStackTrace();
		}
	}
}
