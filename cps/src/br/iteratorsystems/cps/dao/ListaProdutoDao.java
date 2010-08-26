package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.ListaProduto;

/**
 * Classe Dao de lista de produto.
 * @author André
 *
 */
public class ListaProdutoDao extends DaoGeneric<ListaProduto, Integer> {

	/**
	 * Construtor padrão
	 * @param persistentClass - persistentClass
	 * @param session - session
	 */
	public ListaProdutoDao(Class<ListaProduto> persistentClass, Session session) {
		super(persistentClass, session);
	}
}
