package br.iteratorsystems.cps.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.dao.BuscarCepBaseDao;
import br.iteratorsystems.cps.entities.Cep;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;

/**
 * Classe de serviço que busca um cep na base de dados do cps.
 * @author André
 *
 */
public class BuscarCepBaseServiceImpl {
	
	private static final Log log = LogFactory.getLog(BuscarCepBaseServiceImpl.class);
	
	/**
	 * Busca os dados de um cep na base de dados do cps.
	 * @param cep - Cep a procurar
	 * @return Dados de um Cep.
	 * @throws CpsHandlerException - Se ocorrer alguma exceção na base de dados.
	 */
	public Cep buscarCep(String cep) throws CpsHandlerException{
		log.debug("buscando na base o cep "+cep);
		Cep cepObj = null;
		try{
			BuscarCepBaseDao cepDao = new BuscarCepBaseDao();
			cepObj = cepDao.buscarCep(cep);
			log.debug("sucesso: "+cepObj);	
		}catch (CpsDaoException e) {
			log.error("erro ao buscar cep na base "+e);
			throw new CpsHandlerException(e);
		} 
		return cepObj;
	}
}
