package br.iteratorsystems.cps.handler;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ProdutoGeralDao;
import br.iteratorsystems.cps.entities.ProdutoGeral;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;

public class BuscaProdutoHandler {
	
	private static final Log log = LogFactory.getLog(BuscaProdutoHandler.class);
	
	public List<ProdutoGeral> buscaProduto(String partName) throws CpsHandlerException {
		final String message = "finding product with part name: "+partName;
		log.debug(message);
		List<ProdutoGeral> produtos = null;
		ProdutoGeralDao produtoDao = null;
		try{
			produtoDao = new ProdutoGeralDao(ProdutoGeral.class,HibernateConfig.getSession());
			produtos = produtoDao.buscaProduto(partName);
			log.debug("success!");
			return produtos;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public static void main(String[] args) throws CpsHandlerException{
		BuscaProdutoHandler h = new BuscaProdutoHandler();
		List<ProdutoGeral> l = h.buscaProduto("nestle mucilon 400 sapato havainas");
		for(ProdutoGeral p : l){
			System.out.println(p);
		}
		System.out.println("Resultados: "+l.size());
	}
}
