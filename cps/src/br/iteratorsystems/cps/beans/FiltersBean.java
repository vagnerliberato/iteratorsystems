package br.iteratorsystems.cps.beans;

import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;

/**
 * Classe bean da página de filtros de comparação
 * @author André
 *
 */
public class FiltersBean {
	
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private Boolean buscarPeloMenorPreco;
	private Boolean buscarPelaMenorDistancia;
	private FindAddress findAddress;
	private Tabelas_Usuario usuario; 
	
	/**
	 * Busca um cep
	 */
	public void find(){
		findAddress = new FindAddress();
		findAddress.find(this.getCep());
		
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
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Tabelas_Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the usuario
	 */
	public Tabelas_Usuario getUsuario() {
		return usuario;
	}
}
