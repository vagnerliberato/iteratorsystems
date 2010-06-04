package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ProdutoGeralDao extends DaoGeneric<PRODUTOGERAL, Integer> {

	public ProdutoGeralDao(Class<PRODUTOGERAL> persistentClass, Session session) {
		super(persistentClass, session);
	}

	@SuppressWarnings("unchecked")
	public List<PRODUTOGERAL> buscaProduto(String partName) throws CpsDaoException {
		List<PRODUTOGERAL> produtos = null;
		StringBuilder querySQL = new StringBuilder();
		querySQL.append("from PRODUTOGERAL as entity where entity.descricao like upper(:nome)");

		try {
			String[] pedacos = partName.split("\\s");

			for (int index = 0; index < pedacos.length; index++) {
				querySQL.append(" and entity.descricao like upper(:nome"+ index + ")");
			}

			Query query = this.getSession().createQuery(querySQL.toString());

			for (int index = 0; index < pedacos.length; index++) {
				query.setParameter("nome", "%" + pedacos[index] + "%");
				query.setParameter("nome" + index, "%" + pedacos[index] + "%");
			}

			produtos = query.list();
		} catch (Exception e) {
			throw new CpsDaoException(e);
		}
		return produtos;
	}

}
