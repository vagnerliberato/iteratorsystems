package br.iteratorsystems.cps.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.entities.Tabelas_Parametrizacao;
import br.iteratorsystems.cps.entities.Tabelas_ProdutoGeral;
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
	
	private static final String VARIAVEL_FOTO = "image?file=";
	private static final String VARIAVEL_CAMINHO_FOTO = "&amp;dir=";
	private static final String TIPO_IMAGEM = ".jpg";
	
	private BuscaProdutoHandler buscaProdutoHandler;
	private String produtoDigitado;
	private String nomeModalQuantidade;
	private String diretorioImagem;
	private Integer numeroMaximoItensCarrinho;
	private List<ProdutoTO> listaProdutoTO;
	private List<ProdutoTO> produtosCarrinho = new ArrayList<ProdutoTO>();
	private ProdutoTO produtoCarrinhoSelecionado;
	private HtmlDataTable listaProdutosDataTable;
	private boolean showQuantidade;
	private boolean nenhumRegistroEncontrado;
	
	/**
	 * Construtor padrão.
	 */
	public DefaultBean() {
		parametrizarBusca(obterParametrizacao());
	}
	
	/**
	 * Obtém a parametrização do sistema
	 * @return Classe de parametrização do sistema.
	 */
	private Tabelas_Parametrizacao obterParametrizacao() {
		Tabelas_Parametrizacao parametrizacao = (Tabelas_Parametrizacao) FacesContext
				.getCurrentInstance().getExternalContext().getApplicationMap()
				.get("parametrizacao");
		return parametrizacao;
	}
	
	/**
	 * Com base na parametrização do sistema, configura os itens necessários.
	 * @param parametrizacao - Classe de parametrização.
	 */
	private void parametrizarBusca(Tabelas_Parametrizacao parametrizacao) {
		this.setNumeroMaximoItensCarrinho(
					Integer.parseInt(parametrizacao.getNumMaxItensLista().trim()));
		this.setDiretorioImagem(parametrizacao.getDiretorioImagensProCps());
	}
	
	/**
	 * Busca os produtos com base no que foi digitado pelo usuário.
	 * @throws CpsGeneralExceptions Alguma exceção, verificada ou não nas
	 * camadas abaixo do Bean.
	 */
	public void buscarProduto() throws CpsGeneralExceptions{
		buscaProdutoHandler = new BuscaProdutoHandler();
		List<Tabelas_ProdutoGeral> listaTemp = null;
		listaProdutoTO = new ArrayList<ProdutoTO>(1);

		try{
			listaTemp = 
				buscaProdutoHandler.buscaProduto(this.getProdutoDigitado());
			
			if(listaTemp == null || listaTemp.isEmpty()) {
				this.setNenhumRegistroEncontrado(true);
			}else{
				this.setNenhumRegistroEncontrado(false);
			}
			
			for(Tabelas_ProdutoGeral produtoGeral : listaTemp) {
				ProdutoTO produtoTO = new ProdutoTO();
				produtoTO.setProdutoGeral(produtoGeral);
				produtoTO.setQuantidadeSelecionada(1);
				
				if(produtoGeral.getImagem().toString().equalsIgnoreCase("S")) {
					produtoTO.setImagem(
							criarCaminhoFoto(
									produtoGeral.getCodigoBarras().trim()));
					produtoTO.setPossuiImagem(true);
				}
				listaProdutoTO.add(produtoTO);
			}
			
			listaProdutoTO.removeAll(produtosCarrinho);
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	/**
	 * Cria a String com o caminho da imagem na pasta.
	 * @param codigoProduto - Código do produto para ser usado na busca.
	 * @return Caminho concatenado com o nome do arquivo.
	 */
	private String criarCaminhoFoto(final String codigoProduto) {
		StringBuilder builder = new StringBuilder();
		builder.append(VARIAVEL_FOTO);
		builder.append(codigoProduto);
		builder.append(TIPO_IMAGEM);
		builder.append(VARIAVEL_CAMINHO_FOTO);
		builder.append(getDiretorioImagem().replace(File.separator,""));
		return builder.toString();
	}
	
	/**
	 * Adiciona o produto escolhido pelo usuário no carrinho.
	 */
	public void adicionarCarrinho() {
		ProdutoTO produtoSelecionado  = (ProdutoTO) this.listaProdutosDataTable.getRowData();
		int quantidadeSelecionada = produtoSelecionado.getQuantidadeSelecionada();
		nomeModalQuantidade = "";
		if(quantidadeSelecionada < 1) {
			nomeModalQuantidade = "Richfaces.showModalPanel('modalInfoQuantidade');";
		}else {
			if(produtosCarrinho.size() +1 > getNumeroMaximoItensCarrinho()) {
				nomeModalQuantidade = "Richfaces.showModalPanel('modalQuantidadeCarrinho');";
			}else {
				if(!produtosCarrinho.contains(produtoSelecionado)) {
					produtosCarrinho.add(produtoSelecionado);
					listaProdutoTO.remove(produtoSelecionado);
				}
			}
		}
	}
	
	/**
	 * Obtem a lista de sugestões com base no que foi digitado pelo usuário.
	 * @param obj - palavra digitada pelo usuário.
	 * @return Lista de sugestões.
	 */
	public List<String> recuperarAutoComplete(Object obj) {
		List<String> listaDadosTemp = new ArrayList<String>();
		List<String> listaNomeProdutoTemp = new ArrayList<String>();
		List<Tabelas_ProdutoGeral> listaProdutoTemp = null;
		buscaProdutoHandler = new BuscaProdutoHandler();
		String partName = (String) obj;
		
     	try {
     		
     		listaProdutoTemp =
     					buscaProdutoHandler.buscaProduto(partName);
     		
     		for(Tabelas_ProdutoGeral produtoTemp : listaProdutoTemp) {
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
	 * @param diretorioImagem the diretorioImagem to set
	 */
	public void setDiretorioImagem(String diretorioImagem) {
		this.diretorioImagem = diretorioImagem;
	}

	/**
	 * @return the diretorioImagem
	 */
	public String getDiretorioImagem() {
		return diretorioImagem;
	}

}
