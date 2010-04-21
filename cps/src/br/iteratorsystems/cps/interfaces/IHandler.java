package br.iteratorsystems.cps.interfaces;

import java.util.Collection;

import br.iteratorsystems.cps.exceptions.CpsHandlerException;

/**
 * Interface para as operações básicas de um handler, as demais terão seus
 * métodos particulares.
 * 
 * @author André
 * 
 */
public interface IHandler {

	/**
	 * Método utilizado para incluir um registro no banco.
	 * @param instance Objeto a ser excluido.
	 * @throws CpsHandlerException Se algum erro ocorrer na camada handler da aplicação.
	 */
	void save(Object instance) throws CpsHandlerException;

	/**
	 * Método utilizado para recuperar um registro do banco com base em um paramêtro.
	 * @param instance - Parametro a ser passado ao banco.
	 * @return Objeto a ser recuperado do banco.
	 * @throws CpsHandlerException Se algum erro ocorrer na camada handler da aplicação.
	 */
	Object get(Object instance) throws CpsHandlerException;

	/**
	 * Método utilizado para recuperar objetos do banco com base em um parâmetro.
	 * @return - Uma coleção de objetos encontrados.
	 * @throws CpsHandlerException Se algum erro ocorrer na camada handler da aplicação.
	 */
	Collection<Object> getAll() throws CpsHandlerException;

	/**
	 * Método utilizado para persistir um objeto no banco.
	 * @param instance Objeto a ser persistido no banco
	 * @throws CpsHandlerException Se algum erro ocorrer na camada handler da aplicação.
	 */
	void update(Object instance) throws CpsHandlerException;

	/**
	 * Método utilizado para excluir um registro do banco
	 * @param instance Objeto a ser excluido do banco.
	 * @throws CpsHandlerException Se algum erro ocorrer na camada handler da aplicação.
	 */
	void delete(Object instance) throws CpsHandlerException;
}
