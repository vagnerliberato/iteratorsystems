package br.iteratorsystems.cps.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlExtendedDataTable;

import br.iteratorsystems.cps.entities.PARAMETRIZACAO_CPS;
import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.BuscaProdutoHandler;
import br.iteratorsystems.cps.to.ProdutoTO;

/**
 * Classe Responsável por gerenciar a página default do sistema.
 * @author André
 *
 */
public class DefaultBean {
	
	private BuscaProdutoHandler buscaProdutoHandler;
	private String produtoDigitado;
	private String nomeModalQuantidade;
	private List<ProdutoTO> listaProdutoTO;
	private List<ProdutoTO> produtosCarrinho = new ArrayList<ProdutoTO>();
	private ProdutoTO produtoCarrinhoSelecionado;
	private HtmlExtendedDataTable listaProdutosDataTable;
	private boolean showQuantidade;
	//TODO implementar o lock da lista.
	private Integer numeroMaximoItensCarrinho;
	
	/**
	 * Construtor padrão.
	 */
	public DefaultBean() {
		
		PARAMETRIZACAO_CPS parametrizacao = 
			(PARAMETRIZACAO_CPS) 
				FacesContext.getCurrentInstance().
				getExternalContext().getApplicationMap().
				get("parametrizacao");
		
		parametrizarBusca(parametrizacao);
	}
	
	/**
	 * Com base na parametrização do sistema, configura os itens necessários.
	 * @param parametrizacao - Classe de parametrização.
	 */
	private void parametrizarBusca(PARAMETRIZACAO_CPS parametrizacao) {
		this.setNumeroMaximoItensCarrinho(
					Integer.parseInt(parametrizacao.getNumMaxItensLista().trim()));
	}
	
	/**
	 * Busca os produtos com base no que foi digitado pelo usuário.
	 * @throws CpsGeneralExceptions Alguma exceção, verificada ou não nas
	 * camadas abaixo do Bean.
	 */
	public void buscarProduto() throws CpsGeneralExceptions{
		buscaProdutoHandler = new BuscaProdutoHandler();
		List<PRODUTOGERAL> listaTemp = null;
		listaProdutoTO = new ArrayList<ProdutoTO>(1);
		
		try{
			listaTemp = 
				buscaProdutoHandler.buscaProduto(this.getProdutoDigitado());
			
			for(PRODUTOGERAL produtoGeral : listaTemp) {
				listaProdutoTO.add(new ProdutoTO(produtoGeral, 1));
			}
			
			listaProdutoTO.removeAll(produtosCarrinho);
			
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	/**
	 * Adiciona o produto escolhido pelo usuário no carrinho.
	 */
	public void adicionarCarrinho() {
		ProdutoTO produtoSelecionado  = (ProdutoTO) this.listaProdutosDataTable.getRowData();
		int quantidadeSelecionada = produtoSelecionado.getQuantidadeSelecionada();
		
		if(quantidadeSelecionada < 1) {
			nomeModalQuantidade = "modalInfoQuantidade.show();";
		}else {
			nomeModalQuantidade = "";
			if(!produtosCarrinho.contains(produtoSelecionado)) {
				produtosCarrinho.add(produtoSelecionado);
				listaProdutoTO.remove(produtoSelecionado);
			}
		}
	}
	
	public List<String> recuperarAutoComplete(Object obj) {
		List<String> listaDadosTemp = new ArrayList<String>();
		List<String> listaNomeProdutoTemp = new ArrayList<String>();
		List<PRODUTOGERAL> listaProdutoTemp = null;
		buscaProdutoHandler = new BuscaProdutoHandler();
		String partName = (String) obj;
		
     	try {
     		
     		listaProdutoTemp =
     					buscaProdutoHandler.buscaProduto(partName);
     		
     		for(PRODUTOGERAL produtoTemp : listaProdutoTemp) {
     			listaNomeProdutoTemp.add(produtoTemp.getDescricao());
     		}
     		
     		for(String string : listaNomeProdutoTemp) {
     			if(string.toUpperCase().contains(partName.toUpperCase())) {
     				listaDadosTemp.add(string);
     			}
     		}
     		
		} catch (CpsHandlerException e) {
			e.printStackTrace();
		}
		
		return listaDadosTemp;
	}
	
	/**
	 * Exclui um produto do carrinho.
	 */
	public void excluirProdutoCarrinho() {
		produtosCarrinho.remove(produtoCarrinhoSelecionado);
	}
	
	/**
	 * Retorna a quantidade de items do carrinho do usuário.
	 * @return quantidade de itens 
	 */
	public int getTamanhoCarrinho() {
		return this.getProdutosCarrinho().size();
	}
 	
	/**
	 * Se o usuário quiser finalizar a escolha dos produtos, redireciona para
	 * a tela de filtros.
	 * @return String de navegação do JSF.
	 */
	public String toFilters(){
		return "filtersOk";
	}

	/**
	 * @return the produtoDigitado
	 */
	public String getProdutoDigitado() {
		return produtoDigitado;
	}

	/**
	 * @param produtoDigitado the produtoDigitado to set
	 */
	public void setProdutoDigitado(String produtoDigitado) {
		this.produtoDigitado = produtoDigitado;
	}

	/**
	 * @return the listaProdutoTO
	 */
	public List<ProdutoTO> getListaProdutoTO() {
		return listaProdutoTO;
	}

	/**
	 * @param listaProdutoTO the listaProdutoTO to set
	 */
	public void setListaProdutoTO(List<ProdutoTO> listaProdutoTO) {
		this.listaProdutoTO = listaProdutoTO;
	}

	/**
	 * @return the produtosCarrinho
	 */
	public List<ProdutoTO> getProdutosCarrinho() {
		return produtosCarrinho;
	}

	/**
	 * @param produtosCarrinho the produtosCarrinho to set
	 */
	public void setProdutosCarrinho(List<ProdutoTO> produtosCarrinho) {
		this.produtosCarrinho = produtosCarrinho;
	}

	/**
	 * @return the showQuantidade
	 */
	public boolean isShowQuantidade() {
		return showQuantidade;
	}

	/**
	 * @param showQuantidade the showQuantidade to set
	 */
	public void setShowQuantidade(boolean showQuantidade) {
		this.showQuantidade = showQuantidade;
	}

	/**
	 * @return the listaProdutosDataTable
	 */
	public HtmlExtendedDataTable getListaProdutosDataTable() {
		return listaProdutosDataTable;
	}

	/**
	 * @param listaProdutosDataTable the listaProdutosDataTable to set
	 */
	public void setListaProdutosDataTable(HtmlExtendedDataTable listaProdutosDataTable) {
		this.listaProdutosDataTable = listaProdutosDataTable;
	}

	/**
	 * @param nomeModalQuantidade the nomeModalQuantidade to set
	 */
	public void setNomeModalQuantidade(String nomeModalQuantidade) {
		this.nomeModalQuantidade = nomeModalQuantidade;
	}

	/**
	 * @return the nomeModalQuantidade
	 */
	public String getNomeModalQuantidade() {
		return nomeModalQuantidade;
	}

	/**
	 * @param produtoCarrinhoSelecionado the produtoCarrinhoSelecionado to set
	 */
	public void setProdutoCarrinhoSelecionado(ProdutoTO produtoCarrinhoSelecionado) {
		this.produtoCarrinhoSelecionado = produtoCarrinhoSelecionado;
	}

	/**
	 * @return the produtoCarrinhoSelecionado
	 */
	public ProdutoTO getProdutoCarrinhoSelecionado() {
		return produtoCarrinhoSelecionado;
	}

	/**
	 * @param numeroMaximoItensCarrinho the numeroMaximoItensCarrinho to set
	 */
	public void setNumeroMaximoItensCarrinho(Integer numeroMaximoItensCarrinho) {
		this.numeroMaximoItensCarrinho = numeroMaximoItensCarrinho;
	}

	/**
	 * @return the numeroMaximoItensCarrinho
	 */
	public Integer getNumeroMaximoItensCarrinho() {
		return numeroMaximoItensCarrinho;
	}

}
