package br.iteratorsystems.cps.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ProdutoGeralDao extends DaoGeneric<PRODUTOGERAL, String> {

	private static final int BUSCA_POR_DESCRICAO = 1;
	private static final int VALOR_EXATO = 10;
	private static final int BUSCA_INICIO = 20;
	private static final int BUSCA_QUALQUER_POSICAO = 30;
	private static final int BUSCA_FIM = 40;

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
	
	@SuppressWarnings("unchecked")
	public List<PRODUTOGERAL> obterProduto(final String descricao, final Integer tipoDeBusca, final Integer posicaoDaBusca) throws CpsDaoException{
		StringBuilder hql = new StringBuilder();
		hql.append("select p from PRODUTOGERAL as p where						");
		
		if(tipoDeBusca == BUSCA_POR_DESCRICAO){
			hql.append(" p.descricao like upper(:descricao)					");
		}else{
			hql.append(" p.codigoBarras like (:descricao)					");
		}

		hql.append(" order by p.descricao										");
		
		Query query = this.getSession().createQuery(hql.toString());
		switch (posicaoDaBusca) {
			case VALOR_EXATO:
				query.setParameter("descricao", descricao);
				break;
			case BUSCA_INICIO:
				query.setParameter("descricao", descricao + "%");
				break;
			case BUSCA_QUALQUER_POSICAO:
				query.setParameter("descricao", "%" + descricao + "%");
				break;
			case BUSCA_FIM:
				query.setParameter("descricao", "%" + descricao);
				break;
		}
		
		return query.list();
	}	
}
