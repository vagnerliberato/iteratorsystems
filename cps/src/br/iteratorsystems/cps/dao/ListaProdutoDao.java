package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;

/**
 * Classe Dao de lista de produto.
 * @author André
 *
 */
public class ListaProdutoDao extends DaoGeneric<Tabelas_ListaProduto, Integer> {

	/**
	 * Construtor padrão
	 * @param persistentClass - persistentClass
	 * @param session - session
	 */
	public ListaProdutoDao(Class<Tabelas_ListaProduto> persistentClass, Session session) {
		super(persistentClass, session);
	}
}
