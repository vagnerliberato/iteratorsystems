package br.iteratorsystems.cps.beans;

import java.util.List;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.entities.PARAMETRIZACAO_CPS;
import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.helper.ListaProdutoTOHelper;
import br.iteratorsystems.cps.service.ListaProdutoService;
import br.iteratorsystems.cps.to.ProdutoTO;

/**
 * Classe bean responsável pela lista de compras do usuário
 * @author Robson
 *
 */
public class ListaDeProdutoBean {

	private String descricaoProduto;
	private List<PRODUTOGERAL> listaBusca;
	private Set<ProdutoTO> listaComprasUsuario;
	private ListaProdutoService listaProdutoService;
	private HtmlDataTable listaProdutosDataTable;
	private String nomeModalQuantidade;
	private Integer numeroMaximoItensCarrinho;
	
	/**
	 * Construtor padrão	
	 */
	public ListaDeProdutoBean(){
		listaProdutoService = new ListaProdutoService();
		//numeroMaximoItensCarrinho = 
			//Integer.parseInt(obterParametrizacao().getNumMaxItensLista());
	}
	
	/**
	 * Obtém a parametrização do sistema
	 * @return Classe de parametrização do sistema.
	 */
	private PARAMETRIZACAO_CPS obterParametrizacao() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		PARAMETRIZACAO_CPS parametrizacao = (PARAMETRIZACAO_CPS)context.getApplicationMap().get("parametrizacao");
		return parametrizacao;
	}
	
	/**
	 * Exclui uma lista de produto
	 * @throws CpsGeneralExceptions - Se ocorrer alguma exceção na camada abaixo do bean.
	 */
	public void excluirListaDeProdutos() throws CpsGeneralExceptions{
		listaProdutoService.excluirListaDeProdutos(ListaProdutoTOHelper.converteListaProdutoTO(listaComprasUsuario));
	}
	
	/**
	 * Inclui uma lista de produto
	 * @throws CpsGeneralExceptions - Se ocorrer alguma exceção na camada abaixo do bean.
	 */
	public void incluirListaDeProdutos()throws CpsGeneralExceptions{
		listaProdutoService.incluirListaDeProdutos(ListaProdutoTOHelper.converteListaProdutoTO(listaComprasUsuario));
	}
	
	/**
	 * Adiciona um produto na lista de produtos.
	 */
	public void adicionarProdutoNaListaDeProdutos(){
		ProdutoTO produtoTO = (ProdutoTO) this.listaProdutosDataTable.getRowData();
		int quantidadeSelecionada = produtoTO.getQuantidadeSelecionada();
		
		nomeModalQuantidade = "";
		if(quantidadeSelecionada < 1) {
			nomeModalQuantidade = "Richfaces.showModalPanel('modalInfoQuantidade');";
		}else {
			if(listaComprasUsuario.size() +1 > getNumeroMaximoItensCarrinho()) {
				nomeModalQuantidade = "Richfaces.showModalPanel('modalQuantidadeCarrinho');";
			}else {
				if(!listaComprasUsuario.contains(produtoTO)) {
					listaComprasUsuario.add(produtoTO);
				}
			}
		}
	}
	
	/**
	 * Exclui um produto da lista de produto.
	 */
	public void excluirProdutoDaListaDeProduto(){
		ProdutoTO produtoTO = (ProdutoTO) this.listaProdutosDataTable.getRowData();
		listaComprasUsuario.remove(produtoTO);
	}
	
	
	/**
	 * @return the descricaoProduto
	 */
	public String getDescricaoProduto() {
		return descricaoProduto;
	}
	/**
	 * @param descricaoProduto the descricaoProduto to set
	 */
	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}
	/**
	 * @return the listaBusca
	 */
	public List<PRODUTOGERAL> getListaBusca() {
		return listaBusca;
	}
	/**
	 * @param listaBusca the listaBusca to set
	 */
	public void setListaBusca(List<PRODUTOGERAL> listaBusca) {
		this.listaBusca = listaBusca;
	}
	/**
	 * @return the listaComprasUsuario
	 */
	public Set<ProdutoTO> getListaComprasUsuario() {
		return listaComprasUsuario;
	}
	/**
	 * @param listaComprasUsuario the listaComprasUsuario to set
	 */
	public void setListaComprasUsuario(Set<ProdutoTO> listaComprasUsuario) {
		this.listaComprasUsuario = listaComprasUsuario;
	}

	/**
	 * @return the listaProdutoService
	 */
	public ListaProdutoService getListaProdutoService() {
		return listaProdutoService;
	}

	/**
	 * @param listaProdutoService the listaProdutoService to set
	 */
	public void setListaProdutoService(ListaProdutoService listaProdutoService) {
		this.listaProdutoService = listaProdutoService;
	}

	/**
	 * @return the listaProdutosDataTable
	 */
	public HtmlDataTable getListaProdutosDataTable() {
		return listaProdutosDataTable;
	}

	/**
	 * @param listaProdutosDataTable the listaProdutosDataTable to set
	 */
	public void setListaProdutosDataTable(HtmlDataTable listaProdutosDataTable) {
		this.listaProdutosDataTable = listaProdutosDataTable;
	}

	/**
	 * @return the nomeModalQuantidade
	 */
	public String getNomeModalQuantidade() {
		return nomeModalQuantidade;
	}

	/**
	 * @param nomeModalQuantidade the nomeModalQuantidade to set
	 */
	public void setNomeModalQuantidade(String nomeModalQuantidade) {
		this.nomeModalQuantidade = nomeModalQuantidade;
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
