package br.iteratorsystems.cps.beans;

import java.util.List;

import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.BuscaProdutoHandler;

public class DefaultBean {
	
	private BuscaProdutoHandler buscaProdutoHandler;
	
	private String produtoDigitado;
	
	private List<PRODUTOGERAL> produtosPesquisados;

	public void buscaProduto() throws CpsGeneralExceptions{
		buscaProdutoHandler = new BuscaProdutoHandler();
		try{
			produtosPesquisados = 
				buscaProdutoHandler.buscaProduto(this.getProdutoDigitado());
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public String toFilters(){
		return "filtersOk";
	}

	public void setProdutoDigitado(String produtoDigitado) {
		this.produtoDigitado = produtoDigitado;
	}



	public String getProdutoDigitado() {
		return produtoDigitado;
	}

	public void setProdutosPesquisados(List<PRODUTOGERAL> produtosPesquisados) {
		this.produtosPesquisados = produtosPesquisados;
	}

	public List<PRODUTOGERAL> getProdutosPesquisados() {
		return produtosPesquisados;
	}
}
