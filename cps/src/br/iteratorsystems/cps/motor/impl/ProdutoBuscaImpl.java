package br.iteratorsystems.cps.motor.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.service.ProdutoBuscaServiceImpl;
import br.iteratorsystems.cps.to.MercadoTO;
import br.iteratorsystems.cps.to.ProdutoBuscaTO;
import br.iteratorsystems.cps.to.ProdutoTO;
import br.iteratorsystems.cps.to.ResultadoProdutoMercadoTO;

/**
 * Classe de busca de produtos
 * @author André
 *
 */
public class ProdutoBuscaImpl implements Serializable{

	/**
	 * serial
	 */
	private static final long serialVersionUID = 5107512725088620040L;

	private ProdutoBuscaServiceImpl produtoBuscaServiceImpl;
	
	/**
	 * Construtor
	 */
	public ProdutoBuscaImpl() {
		produtoBuscaServiceImpl = new ProdutoBuscaServiceImpl();
	}
	
	/**
	 * Obtem  a lista de produtos por mercado
	 * @param listaMercado - lista de mercado
	 * @param listaProdutos - lista de produtos
	 * @return Resultado da busca de produtos por mercados
	 * @throws CpsExceptions Se ocorrer algum erro.
	 */
	public List<ResultadoProdutoMercadoTO> obterListaDeProdutosPorMercado(List<MercadoTO> listaMercado,List<ProdutoTO> listaProdutos) throws CpsExceptions {
		List<ResultadoProdutoMercadoTO> listaResultadoMercado =
							new ArrayList<ResultadoProdutoMercadoTO>();
		
		for (MercadoTO mercadoTO : listaMercado) {
			try {
				listaResultadoMercado.add(produtoBuscaServiceImpl
						.obterListaDeProdutosPorMercado(mercadoTO.getCodigo(),
								mercadoTO.getCodigoRede(), listaProdutos));
			} catch (CpsDaoException e) {
				throw new CpsExceptions(e);
			}
		}
		
		this.somarValorProduto(listaResultadoMercado);
		return listaResultadoMercado;
	}

	/**
	 * Soma o valor dos produtos
	 * @param listaResultadoMercado - lista de mercado
	 */
	private void somarValorProduto(
			List<ResultadoProdutoMercadoTO> listaResultadoMercado) {
		
		for(ResultadoProdutoMercadoTO resultado : listaResultadoMercado) {
			Double valorTotal = 0d;
			for(ProdutoBuscaTO produtoBuscaTO : resultado.getListaEncontrados()) {
				valorTotal += produtoBuscaTO.getValor();
			}
			resultado.setValorTotalLista(valorTotal);
		}
		
		Collections.sort(listaResultadoMercado);
	}
}
