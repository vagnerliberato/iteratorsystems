package br.iteratorsystems.cps.beans;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.ListaProduto;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Parametrizacao;
import br.iteratorsystems.cps.enums.TipoDeComparacao;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.helper.FormatadorEstadorHelper;
import br.iteratorsystems.cps.helper.ListaProdutoTOHelper;
import br.iteratorsystems.cps.interfaces.MotorComparacao;
import br.iteratorsystems.cps.motor.MotorComparacaoImpl;
import br.iteratorsystems.cps.to.ProdutoTO;
import br.iteratorsystems.cps.to.ResultadoComparacaoTO;

/**
 * Classe bean da página de filtros de comparação
 * @author André
 *
 */
public class FiltersBean {
	
	private static final int VALOR_MAX_LOJA_COMPARACAO = 5;
	
	private String cep;
	private String cepAlternativo;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private String campoHidden;
	private String valorCampoComparacao;
	private String valorInformacaoModal;
	private Boolean buscarPeloMenorPreco;
	private Boolean buscarPelaMenorDistancia;
	private FindAddress findAddress;
	private Login login;
	private List<String> listasUsuario;
	private List<ResultadoComparacaoTO> listaComparacao;
	private String produtoSelecionadoCombo;

	private MotorComparacao motorComparacao;
	private Parametrizacao parametrizacao;
	
	private boolean renderizarGif;
	
	/**
	 * Carrega o combo da lista de usuario.
	 */
	private void carregarComboProdutosUsuario() {
		List<ListaProduto> listaProdutos = null;
		if(login != null) {
			if(login.getUsuario() != null) {
				listaProdutos = 
					new ArrayList<ListaProduto>(login.getUsuario().getListaProdutos());
				
				listasUsuario = new ArrayList<String>();
				if(verificarCarrinhoPreenchido()) {
					listasUsuario.add("Usar meu carrinho");
				}
				
				for(ListaProduto lista : listaProdutos) {
					listasUsuario.add(lista.getId()+" - "+lista.getNomeLista());
				}
			}
		}
	}

	/**
	 * Cria o motor de comparação de produtos
	 */
	public String compararProdutos() {
		this.limparDadosDefault();
		String retorno = "";
		if(verificarCamposPreenchidos()) {
			if (buscarPeloMenorPreco) {
				retorno = comparar(TipoDeComparacao.MENOR_PRECO);
			} else if (buscarPelaMenorDistancia) {
				retorno = comparar(TipoDeComparacao.MENOR_DISTANCIA);
			} else {
				retorno = comparar(TipoDeComparacao.MENOR_PRECO_E_DISTANCIA);
			}
		}
		renderizarGif = false;
		return retorno;
	}

	/**
	 * Renderiza o gif
	 */
	public void renderizarImagem() {
		renderizarGif = true;
	}
	
	private void limparDadosDefault() {//TODO verificar outra forma
		FacesContext context = FacesContext.getCurrentInstance();
		ELResolver el = context.getApplication().getELResolver();
		DefaultBean defaultBean = (DefaultBean) el.getValue(context.getELContext(),null,"defaultBean");
		defaultBean.limparBusca();
	}

	/**
	 * Verifica se os campos da página foram preenhcidos corretamente.
	 * @return Se foram ou não
	 */
	private boolean verificarCamposPreenchidos() {
		 boolean correto = true;
		if(cep == null || cep.isEmpty()) {
			correto = false;
			valorInformacaoModal = "Informe um cep válido.";
			valorCampoComparacao = "Richfaces.showModalPanel('modalErroPreenchimento');";
		}else{
			valorInformacaoModal = "";
			valorCampoComparacao = "";
		}
		
		if(correto) {
			if(!buscarPeloMenorPreco && !buscarPelaMenorDistancia) {
				
				correto = false;
				valorInformacaoModal = "Selecione busca pelo menor preço, ou distância, ou os dois.";
				valorCampoComparacao = "Richfaces.showModalPanel('modalErroPreenchimento');";
			}else{
				valorInformacaoModal = "";
				valorCampoComparacao = "";
			}
		}
		
		if(correto) {
			if(login != null && (produtoSelecionadoCombo == null || 
					produtoSelecionadoCombo.isEmpty())) {
				
				correto = false;
				valorInformacaoModal = "Selecione uma lista, ou o carrinho para comparação";
				valorCampoComparacao = "Richfaces.showModalPanel('modalErroPreenchimento');";
				
			}else{
				valorCampoComparacao = "";
				valorInformacaoModal = "";
			}
		}
		return correto;
	}
	
	/**
	 * Compara os produtos do usuario
	 * @param tipoDeComparacao - tipo de comparacao
	 */
	private String comparar(TipoDeComparacao tipoDeComparacao) {
		String retorno = "toResultPage";
		this.obterParametrizacao();
		motorComparacao = 
			new MotorComparacaoImpl(
					verificarProdutoParaComparacao(),
					tipoDeComparacao,verificarUFUsuario(),
					obterMaxLojasComparacao(),
					obterValorMedioCombustivel(),
					obterRendimentoCombustivel(),
					obterCepUsuarioComparacao());
		try {
			listaComparacao =
						motorComparacao.comparar();
			this.setValorCampoComparacao("submit();");
		} catch (CpsExceptions e) {
			retorno = "toDefaultPage";
			this.setValorCampoComparacao("");
			e.printStackTrace();
		}
		return retorno;
	}
	
	/**
	 * Obtem o cep do usuario
	 * @return Cep
	 */
	private String obterCepUsuarioComparacao() {
		String cep = null;
		if(cepAlternativo != null && !cepAlternativo.isEmpty()) {
			cep = cepAlternativo;
		}else{
			cep = this.getCep();
		}
		return cep;
	}
	
	/**
	 * Obtem o rendimento medio do cumbustivel, em KM/Litros
	 * @return Valor do rendimento
	 */
	private Double obterRendimentoCombustivel() {
		Double valor  = 0d;
		valor = parametrizacao.getRendimentomediocombustivel();
		return valor;
	}
	
	/**
	 * Obtém o valor médio do combustivel em Litros.
	 * @return Valor médio
	 */
	private Double obterValorMedioCombustivel() {
		Double valor = 0d;
		valor = parametrizacao.getValormediocombustivel();
		return valor;
	}
	
	/**
	 * Obtem a parametrizacao do sistema
	 */
	private void obterParametrizacao() {
		parametrizacao = (Parametrizacao) FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap().get("parametrizacao");
	}

	/**
	 * Obtem a parametrização para o maximo de lojas.
	 * @return Valor de quantidade de lojas.
	 */
	private Integer obterMaxLojasComparacao() {
		Integer valor = VALOR_MAX_LOJA_COMPARACAO; // valor default
		
		if(parametrizacao.getNumMaxLojasComparacao() != null) {
			valor = Integer.parseInt(parametrizacao.getNumMaxLojasComparacao().trim());
		}
		
		return valor;
	}
	
	/**
	 * Verifica a UF do usuario
	 * @return Uf do usuario
	 */
	private String verificarUFUsuario() {
		String uf = null;
		uf = 
			FormatadorEstadorHelper.obterSiglaEstado(this.getEstado());
		return uf;
	}
	
	/**
	 * Verifica qual lista de produtos ou carrinho sera usado para comparacao.
	 * @return Lista de produto TO
	 */
	private List<ProdutoTO> verificarProdutoParaComparacao() {
		List<ProdutoTO> listaProdutoTO = null;
		DefaultBean defaultBean = null;
		
		if(login != null) {
			List<ListaProduto> listaProduto = 
				new ArrayList<ListaProduto>(login.getUsuario().getListaProdutos());
			
			if(verificarEscolhaUsuario()) {
				for(ListaProduto listaProdutoObj : listaProduto) {
					if(listaProdutoObj.getId().compareTo(Integer.parseInt(obterCodigoListaCombo())) == 0) {
						listaProdutoTO = 
							new ArrayList<ProdutoTO>(
									ListaProdutoTOHelper.converteItemLista(listaProdutoObj.getListaProdutoItems()));
					}
				}
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
				ELResolver el = context.getApplication().getELResolver();
				defaultBean = (DefaultBean) el.getValue(context.getELContext(),null,"defaultBean");
				
				listaProdutoTO = defaultBean.getProdutosCarrinho();
			}
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			ELResolver el = context.getApplication().getELResolver();
			defaultBean = (DefaultBean) el.getValue(context.getELContext(),null,"defaultBean");
			
			listaProdutoTO = defaultBean.getProdutosCarrinho();
		}
		return listaProdutoTO;
	}

	/**
	 * Verifica se o usuario logado escolheu uma lista ou carrinho.
	 * @return Verdadeiro ou falso.
	 */
	private boolean verificarEscolhaUsuario() {
		boolean lista = true;
		try{
			Integer.parseInt(obterCodigoListaCombo());
		}catch (Exception e) {
			lista = false; 
		}
		return lista;
	}
	
	/**
	 * Obtem o codigo da lista do combo
	 * @return Codigo
	 */
	private String obterCodigoListaCombo() {
		String codigo = null;
		String [] partes = produtoSelecionadoCombo.split("[-]");
		
		if(partes.length > 1) {
			codigo = partes[0].trim();
		}
		return codigo;
	}
	
	/**
	 * Verifica se o carrinho do usuário foi preenchido.
	 * @return Se foi ou não preenchido.
	 */
	private boolean verificarCarrinhoPreenchido() {
		boolean carrinhoCheio = false;
		
		FacesContext context = FacesContext.getCurrentInstance();
		ELResolver el = context.getApplication().getELResolver();
		DefaultBean defaultBean = 
					(DefaultBean) el.getValue(context.getELContext(),null,"defaultBean");
		
		if(defaultBean != null) {
			if(defaultBean.getProdutosCarrinho() != null &&
					!defaultBean.getProdutosCarrinho().isEmpty()) {
				carrinhoCheio = true;
			}
		}
		return carrinhoCheio;
	}
	
	/**
	 * verifica se o usuário esta logado na aplicação,
	 * para abrir a página com suas características.
	 */
	private void verificarUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		login = (Login) servletContext.getAttribute("usuarioLogado");
		
		if(login!=null && cepAlternativo == null) {
			if(login.getUsuario()!=null) {
				for(Endereco endereco : login.getUsuario().getEnderecos()) {
					this.setBairro(endereco.getBairro());
					this.setCep(endereco.getCep());
					this.setCidade(endereco.getCidade());
					this.setEstado(FormatadorEstadorHelper.recuperaEstado(endereco.getEstado()));
					this.setLogradouro(endereco.getLogradouro());
				}
			}
		}
	}
	
	/**
	 * Mostra a pagina de carrinho para visualização dos itens
	 * @return String com a navigation rule
	 */
	public String visualizarCarrinho() {
		return "toMyCar";
	}
	
	/**
	 * Busca um cep
	 */
	public void find(){
		findAddress = new FindAddress();
		
		if(cepAlternativo != null && !"".equals(cepAlternativo)) {
			findAddress.find(this.getCepAlternativo());
		}else {
			findAddress.find(this.getCep());
		}

		this.setLogradouro(findAddress.getLogradouro());
		this.setBairro(findAddress.getBairro());
		this.setCidade(findAddress.getCidade());
		this.setEstado(findAddress.getEstado());
		findAddress = null;
	}
	
	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}
	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}
	/**
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}
	/**
	 * @param logradouro the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}
	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}
	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the buscarPeloMenorPreco
	 */
	public Boolean getBuscarPeloMenorPreco() {
		return buscarPeloMenorPreco;
	}

	/**
	 * @param buscarPeloMenorPreco the buscarPeloMenorPreco to set
	 */
	public void setBuscarPeloMenorPreco(Boolean buscarPeloMenorPreco) {
		this.buscarPeloMenorPreco = buscarPeloMenorPreco;
	}

	/**
	 * @return the buscarPelaMenorDistancia
	 */
	public Boolean getBuscarPelaMenorDistancia() {
		return buscarPelaMenorDistancia;
	}

	/**
	 * @param buscarPelaMenorDistancia the buscarPelaMenorDistancia to set
	 */
	public void setBuscarPelaMenorDistancia(Boolean buscarPelaMenorDistancia) {
		this.buscarPelaMenorDistancia = buscarPelaMenorDistancia;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(Login usuario) {
		this.login = usuario;
	}

	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param campoHidden the campoHidden to set
	 */
	public void setCampoHidden(String campoHidden) {
		this.campoHidden = campoHidden;
	}

	/**
	 * @return the campoHidden
	 */
	//TODO existe uma forma melhor do que essa :*)
	public String getCampoHidden() {
		verificarUsuarioLogado();
		carregarComboProdutosUsuario();
		return campoHidden;
	}

	/**
	 * @param cepAlternativo the cepAlternativo to set
	 */
	public void setCepAlternativo(String cepAlternativo) {
		this.cepAlternativo = cepAlternativo;
	}

	/**
	 * @return the cepAlternativo
	 */
	public String getCepAlternativo() {
		return cepAlternativo;
	}

	/**
	 * @param listasUsuario the listasUsuario to set
	 */
	public void setListasUsuario(List<String> listasUsuario) {
		this.listasUsuario = listasUsuario;
	}

	/**
	 * @return the listasUsuario
	 */
	public List<String> getListasUsuario() {
		return listasUsuario;
	}

	/**
	 * @param produtoSelecionadoCombo the produtoSelecionadoCombo to set
	 */
	public void setProdutoSelecionadoCombo(String produtoSelecionadoCombo) {
		this.produtoSelecionadoCombo = produtoSelecionadoCombo;
	}

	/**
	 * @return the produtoSelecionadoCombo
	 */
	public String getProdutoSelecionadoCombo() {
		return produtoSelecionadoCombo;
	}

	/**
	 * @param listaComparacao the listaComparacao to set
	 */
	public void setListaComparacao(List<ResultadoComparacaoTO> listaComparacao) {
		this.listaComparacao = listaComparacao;
	}

	/**
	 * @return the listaComparacao
	 */
	public List<ResultadoComparacaoTO> getListaComparacao() {
		return listaComparacao;
	}

	/**
	 * @param valorCampoComparacao the valorCampoComparacao to set
	 */
	public void setValorCampoComparacao(String valorCampoComparacao) {
		this.valorCampoComparacao = valorCampoComparacao;
	}

	/**
	 * @return the valorCampoComparacao
	 */
	public String getValorCampoComparacao() {
		return valorCampoComparacao;
	}

	/**
	 * @param valorInformacaoModal the valorInformacaoModal to set
	 */
	public void setValorInformacaoModal(String valorInformacaoModal) {
		this.valorInformacaoModal = valorInformacaoModal;
	}

	/**
	 * @return the valorInformacaoModal
	 */
	public String getValorInformacaoModal() {
		return valorInformacaoModal;
	}

	/**
	 * @param renderizarGif the renderizarGif to set
	 */
	public void setRenderizarGif(boolean renderizarGif) {
		this.renderizarGif = renderizarGif;
	}

	/**
	 * @return the renderizarGif
	 */
	public boolean isRenderizarGif() {
		return renderizarGif;
	}
}