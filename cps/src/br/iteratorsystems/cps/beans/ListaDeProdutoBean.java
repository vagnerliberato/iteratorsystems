package br.iteratorsystems.cps.beans;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.entities.ListaProduto;
import br.iteratorsystems.cps.entities.Parametrizacao;
import br.iteratorsystems.cps.entities.ProdutoGeral;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.BuscaProdutoHandler;
import br.iteratorsystems.cps.handler.LoginUserHandler;
import br.iteratorsystems.cps.helper.ListaProdutoTOHelper;
import br.iteratorsystems.cps.service.ListaProdutoService;
import br.iteratorsystems.cps.to.ListaProdutoTO;
import br.iteratorsystems.cps.to.ProdutoTO;

/**
 * Classe bean responsável pela lista de compras do usuário
 * @author Robson
 *
 */
public class ListaDeProdutoBean {

	private String descricaoProduto;
	private String produtoDigitado;
	private String nomeLista;
	private String paginaAtual;
	private List<ProdutoTO> listaBusca;
	private List<ProdutoTO> listaPagina;
	private List<ListaProduto> listaProdutoUsuario;
	private ListaProdutoTO listaComprasUsuario;
	private ListaProdutoService listaProdutoService;
	private BuscaProdutoHandler buscaProdutoHandler;
	private LoginUserHandler userHandler;
	private HtmlDataTable tabelasListaDataTable;
	private HtmlDataTable listaProdutosDataTable;
	private Usuario usuario;
	private ProdutoTO produtoListaSelecionado;
	private String nomeModalQuantidade;
	private Integer numeroMaximoItensCarrinho;
	private boolean nenhumRegistroEncontrado;
	
	/**
	 * Construtor padrão	
	 */
	public ListaDeProdutoBean(){
		listaProdutoService = new ListaProdutoService();
		userHandler = new LoginUserHandler();
		numeroMaximoItensCarrinho = Integer.parseInt(obterParametrizacao().getNumMaxItensLista().trim());
		instanciarListaDeCompras();
		verificarListaExistente();
	}
	
	/**
	 * Verifica se o usuário tem lista de compras e mostra uma página
	 * personalizada. 
	 */
	private void verificarListaExistente() {
		setUsuario(recuperarUsuario());
		if(usuario == null ||
				usuario.getListaProdutos() == null ||
				usuario.getListaProdutos().isEmpty()) {
			setPaginaAtual("newList.jsf");
		}else{
			setPaginaAtual("allLists.jsf");
			listaPagina.clear();
			listaProdutoUsuario = 
				new ArrayList<ListaProduto>(
						usuario.getListaProdutos());
		}
	}
	
	/**
	 * Instancia a classe de lista de compras.
	 */
	private void instanciarListaDeCompras(){
		listaComprasUsuario = new ListaProdutoTO();
		listaPagina = new ArrayList<ProdutoTO>();
		if(listaComprasUsuario.getListaProdutos() == null) {
			listaComprasUsuario.setListaProdutos(new ArrayList<ProdutoTO>());
		}
	}
	
	/**
	 * Cria uma lista de compras para o usuário.
	 */
	public void criarLista() {
		listaComprasUsuario = new ListaProdutoTO();
		listaComprasUsuario.setNomeLista(this.getNomeLista());
		listaComprasUsuario.setListaProdutos(new ArrayList<ProdutoTO>());
	}
	
	/**
	 * Obtem uma lista de produto para edição.
	 */
	public void obterListaDeProduto() {
		ListaProduto listaTemp = (ListaProduto) tabelasListaDataTable.getRowData();
		listaPagina = (List<ProdutoTO>) 
				ListaProdutoTOHelper.converteItemLista(
						listaTemp.getListaProdutoItems());
	}
	
	/**
	 * Exclui uma lista de compras do usuário.
	 */
	public void excluirLista() {
		if(listaComprasUsuario != null) {
			if(listaComprasUsuario.getListaProdutos() != null) {
				listaComprasUsuario.getListaProdutos().clear();
			}
			listaComprasUsuario.setNomeLista(null);
			this.setNomeLista(null);
		}
		
		if(listaPagina != null) {
			listaPagina.clear();
		}if(listaBusca != null) {
			listaBusca.clear();
		}
	}
	
	/**
	 * Busca os produtos com base no que foi digitado pelo usuário.
	 * @throws CpsGeneralExceptions Alguma exceção, verificada ou não nas
	 * camadas abaixo do Bean.
	 */
	public void buscarProduto() throws CpsGeneralExceptions{
		buscaProdutoHandler = new BuscaProdutoHandler();
		List<ProdutoGeral> listaTemp = null;
		listaBusca = new ArrayList<ProdutoTO>(1);

		try{
			listaTemp = 
				buscaProdutoHandler.buscaProduto(this.getProdutoDigitado());
			
			if(listaTemp == null || listaTemp.isEmpty()) {
				this.setNenhumRegistroEncontrado(true);
			}else{
				this.setNenhumRegistroEncontrado(false);
			}
			
			for(ProdutoGeral produtoGeral : listaTemp) {
				ProdutoTO produtoTO = new ProdutoTO();
				produtoTO.setProdutoGeral(produtoGeral);
				produtoTO.setQuantidadeSelecionada(1);
				listaBusca.add(produtoTO);
			}
			
			listaBusca.removeAll(listaPagina);
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	/**
	 * Obtém a parametrização do sistema
	 * @return Classe de parametrização do sistema.
	 */
	private Parametrizacao obterParametrizacao() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		Parametrizacao parametrizacao = (Parametrizacao)context.getApplicationMap().get("parametrizacao");
		return parametrizacao;
	}
	
	/**
	 * Exclui uma lista de produto
	 * @throws CpsGeneralExceptions - Se ocorrer alguma exceção na camada abaixo do bean.
	 */
	public void excluirListaDeProdutos() throws CpsGeneralExceptions{
//		listaProdutoService.excluirListaDeProdutos(
				//ListaProdutoTOHelper.converteListaProdutoTO(listaComprasUsuario.getListaProdutos()));
	}
	
	/**
	 * Inclui uma lista de produto
	 * @throws CpsGeneralExceptions - Se ocorrer alguma exceção na camada abaixo do bean.
	 */
	public void incluirListaDeProdutos()throws CpsGeneralExceptions{
		listaComprasUsuario.setListaProdutos(listaPagina);
		listaProdutoService.incluirListaDeProdutos(
				ListaProdutoTOHelper.popularUmaListaDeProduto(this.getNomeLista(), getUsuario()),
				ListaProdutoTOHelper.converteListaProdutoTO(listaComprasUsuario.getListaProdutos()));
		verificarListaExistente();
	}
	
	/**
	 * Recupera o usuário ativo na sessão.
	 * @return - Entidade com os dados do usuário.
	 */
	private Usuario recuperarUsuario() {
		Usuario usuario = null;
		FacesContext context = FacesContext.getCurrentInstance();
		ELResolver el = context.getApplication().getELResolver();
		
		LoginUserBean userBean = (LoginUserBean)
					el.getValue(context.getELContext(),null,"loginUserBean");
		
		if(userBean != null) {
			if(userBean.getLogin() != null) {
				usuario = userBean.getUsuario();
				if(usuario == null || (usuario != null && 
						(usuario.getListaProdutos() == null || 
								usuario.getListaProdutos().isEmpty()))) {
					try {
						usuario = userHandler.getUserRelated(
										userBean.getLogin().getIdLogin());
					} catch (CpsHandlerException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return usuario;
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
			if(listaPagina.size() +1 > getNumeroMaximoItensCarrinho()) {
				nomeModalQuantidade = "Richfaces.showModalPanel('modalQuantidadeCarrinho');";
			}else {
				if(!listaPagina.contains(produtoTO)) {
					listaPagina.add(produtoTO);
					listaBusca.remove(produtoTO);
				}
			}
		}
	}
	
	/**
	 * Exclui um produto da lista de produto.
	 */
	public void excluirProdutoDaListaDeProduto(){
		listaPagina.remove(produtoListaSelecionado);
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
	public List<ProdutoTO> getListaBusca() {
		return listaBusca;
	}
	/**
	 * @param listaBusca the listaBusca to set
	 */
	public void setListaBusca(List<ProdutoTO> listaBusca) {
		this.listaBusca = listaBusca;
	}
	/**
	 * @return the listaComprasUsuario
	 */
	public ListaProdutoTO getListaComprasUsuario() {
		return listaComprasUsuario;
	}
	/**
	 * @param listaComprasUsuario the listaComprasUsuario to set
	 */
	public void setListaComprasUsuario(ListaProdutoTO listaComprasUsuario) {
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

	/**
	 * @param nenhumRegistroEncontrado the nenhumRegistroEncontrado to set
	 */
	public void setNenhumRegistroEncontrado(boolean nenhumRegistroEncontrado) {
		this.nenhumRegistroEncontrado = nenhumRegistroEncontrado;
	}

	/**
	 * @return the nenhumRegistroEncontrado
	 */
	public boolean isNenhumRegistroEncontrado() {
		return nenhumRegistroEncontrado;
	}

	/**
	 * @param produtoDigitado the produtoDigitado to set
	 */
	public void setProdutoDigitado(String produtoDigitado) {
		this.produtoDigitado = produtoDigitado;
	}

	/**
	 * @return the produtoDigitado
	 */
	public String getProdutoDigitado() {
		return produtoDigitado;
	}

	/**
	 * @param nomeLista the nomeLista to set
	 */
	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}

	/**
	 * @return the nomeLista
	 */
	public String getNomeLista() {
		return nomeLista;
	}

	/**
	 * @param listaPagina the listaPagina to set
	 */
	public void setListaPagina(List<ProdutoTO> listaPagina) {
		this.listaPagina = listaPagina;
	}

	/**
	 * @return the listaPagina
	 */
	public List<ProdutoTO> getListaPagina() {
		return listaPagina;
	}

	/**
	 * @param produtoListaSelecionado the produtoListaSelecionado to set
	 */
	public void setProdutoListaSelecionado(ProdutoTO produtoListaSelecionado) {
		this.produtoListaSelecionado = produtoListaSelecionado;
	}

	/**
	 * @return the produtoListaSelecionado
	 */
	public ProdutoTO getProdutoListaSelecionado() {
		return produtoListaSelecionado;
	}

	/**
	 * @param paginaAtual the paginaAtual to set
	 */
	public void setPaginaAtual(String paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	/**
	 * @return the paginaAtual
	 */
	public String getPaginaAtual() {
		return paginaAtual;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param userHandler the userHandler to set
	 */
	public void setUserHandler(LoginUserHandler userHandler) {
		this.userHandler = userHandler;
	}

	/**
	 * @return the userHandler
	 */
	public LoginUserHandler getUserHandler() {
		return userHandler;
	}

	/**
	 * @param listaProdutoUsuario the listaProdutoUsuario to set
	 */
	public void setListaProdutoUsuario(List<ListaProduto> listaProdutoUsuario) {
		this.listaProdutoUsuario = listaProdutoUsuario;
	}

	/**
	 * @return the listaProdutoUsuario
	 */
	public List<ListaProduto> getListaProdutoUsuario() {
		return listaProdutoUsuario;
	}

	/**
	 * @param tabelasListaDataTable the tabelasListaDataTable to set
	 */
	public void setTabelasListaDataTable(HtmlDataTable tabelasListaDataTable) {
		this.tabelasListaDataTable = tabelasListaDataTable;
	}

	/**
	 * @return the tabelasListaDataTable
	 */
	public HtmlDataTable getTabelasListaDataTable() {
		return tabelasListaDataTable;
	}
}
