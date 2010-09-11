package br.iteratorsystems.cps.service;

import java.util.List;

import br.iteratorsystems.cps.dao.ProdutoBuscaDAOImpl;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.to.ProdutoTO;
import br.iteratorsystems.cps.to.ResultadoProdutoMercadoTO;

/**
 * Classe de serviço de busca de produtos
 * @author André
 *
 */
public class ProdutoBuscaServiceImpl {

	
	private ProdutoBuscaDAOImpl produtoBuscaDAOImpl;
	
	/**
	 * Construtor
	 */
	public ProdutoBuscaServiceImpl() {
		produtoBuscaDAOImpl = new ProdutoBuscaDAOImpl();
	}
	
	/**
	 * Obtem a lista de produtos encontrados e/ou não encontrados de produto de 
	 * acordo com o id do mercado e da rede.
	 * @param codigoMercado - codigo do mercado
	 * @param codigoRede - codigo da rede
	 * @param listaDeProdutosTO - lista de produtos TO
	 * @return Resultado da busca
	 * @throws CpsDaoException Se ocorrer algum erro
	 */
	public ResultadoProdutoMercadoTO obterListaDeProdutosPorMercado(
			Integer codigoMercado, Integer codigoRede,
			List<ProdutoTO> listaDeProdutosTO) throws CpsDaoException {
		
		return produtoBuscaDAOImpl.obterListaDeProdutosPorMercado(
				codigoMercado, codigoRede, listaDeProdutosTO);
	}
}
