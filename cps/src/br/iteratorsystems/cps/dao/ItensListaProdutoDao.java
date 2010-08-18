package br.iteratorsystems.cps.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.interfaces.EntityAble;

/**
 * Classe dao de itens de lista de produto.
 * @author André
 *
 */
public class ItensListaProdutoDao  extends DaoGeneric<Tabelas_ListaProdutoItem, Integer> {
	
	/**
	 * Construtor padrão.
	 * @param persistentClass - persistentClass
	 * @param session - session
	 */
	public ItensListaProdutoDao(Class<Tabelas_ListaProdutoItem> persistentClass, Session session) {
		super(persistentClass, session);
	}

	/**
	 * Obtém um ultimo id na base referente a entidade.
	 * @param entity - Entidade
	 * @return Id
	 * @throws CpsDaoException - Se ocorrer algum erro.
	 */
	public Integer getLastIdFromModel(EntityAble entity) throws CpsDaoException{
		Integer lastId = null;
		try{
			String query = "select max(model.id) from "+entity.getClass().getSimpleName()+" model";
			Session session = HibernateConfig.getSession();
			Query q = session.createQuery(query);
			lastId = (Integer) q.uniqueResult();
			return lastId;
		}catch (Exception e) {
			throw new CpsDaoException(e);
		}
	}
}
