package br.iteratorsystems.cps.beans;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.common.Carrinho;
import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.BuscaProdutoHandler;

public class DefaultBean {
	
	private BuscaProdutoHandler buscaProdutoHandler;
	
	private String produtoDigitado;
	
	private List<PRODUTOGERAL> produtosPesquisados;
	private List<Carrinho> produtosCarrinho = new ArrayList<Carrinho>();
	
	private HtmlDataTable listaProdutosDataTable;
	
	private boolean showQuantidade;

	public void buscaProduto() throws CpsGeneralExceptions{
		buscaProdutoHandler = new BuscaProdutoHandler();
		try{
			produtosPesquisados = 
				buscaProdutoHandler.buscaProduto(this.getProdutoDigitado());
			
			//produtosPesquisados.clear();
			//JOptionPane.showMessageDialog(null,"A instruçâo á memória EF#$8874 falhou. A memória nâo pode ser 'read'","Windows",JOptionPane.ERROR_MESSAGE);
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void adicionaCarrinho() {
		PRODUTOGERAL produtoGeral  = (PRODUTOGERAL) this.listaProdutosDataTable.getRowData();
		produtosCarrinho.add(new Carrinho(produtoGeral.getCodigoBarras(),1));
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

	public void setShowQuantidade(boolean showQuantidade) {
		this.showQuantidade = showQuantidade;
	}

	public boolean isShowQuantidade() {
		return showQuantidade;
	}

	public void setListaProdutosDataTable(HtmlDataTable listaProdutosDataTable) {
		this.listaProdutosDataTable = listaProdutosDataTable;
	}

	public HtmlDataTable getListaProdutosDataTable() {
		return listaProdutosDataTable;
	}

	public void setProdutosCarrinho(List<Carrinho> produtosCarrinho) {
		this.produtosCarrinho = produtosCarrinho;
	}

	public List<Carrinho> getProdutosCarrinho() {
		return produtosCarrinho;
	}
}
