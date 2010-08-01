package br.iteratorsystems.cps.beans;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.entities.Tabelas_Endereco;
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.helper.FormatadorEstadorHelper;

/**
 * Classe bean da página de filtros de comparação
 * @author André
 *
 */
public class FiltersBean {
	
	private String cep;
	private String cepAlternativo;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private String campoHidden;
	private Boolean buscarPeloMenorPreco;
	private Boolean buscarPelaMenorDistancia;
	private FindAddress findAddress;
	private Tabelas_Login login; 
	
	/**
	 * verifica se o usuário esta logado na aplicação,
	 * para abrir a página com suas características.
	 */
	private void verificarUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		login = (Tabelas_Login) servletContext.getAttribute("usuarioLogado");
		
		if(login!=null) {
			if(login.getUsuario()!=null) {
				for(Tabelas_Endereco endereco : login.getUsuario().getEnderecos()) {
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
	 * Chama o motor de comparação de produtos
	 */
	public void compararProdutos() {
		//TODO comparar
		System.out.println("comparando");
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
	public void setLogin(Tabelas_Login usuario) {
		this.login = usuario;
	}

	/**
	 * @return the login
	 */
	public Tabelas_Login getLogin() {
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
	public String getCampoHidden() {
		verificarUsuarioLogado();
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
}
