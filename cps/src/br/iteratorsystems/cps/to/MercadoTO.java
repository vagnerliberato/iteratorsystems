package br.iteratorsystems.cps.to;

import java.math.BigDecimal;
import java.util.List;

/**
 * Mercado TO
 * @author André
 *
 */
public class MercadoTO{

	private Integer codigo;
	private Integer codigoRede;
	private String nome;
	private String latitude;
	private String longitude;
	private String cep;
	private List<ProdutoTO> listaProdutoEncontrado;
	private List<ProdutoTO> listaProdutoNaoEncontrado;
	private Double valorTotal;
	private Double valorCompras;
	private Double valorDeslocamento;
	private BigDecimal distanciaAproximada;
	
	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}
	/**
	 * @param codigoRede the codigoRede to set
	 */
	public void setCodigoRede(Integer codigoRede) {
		this.codigoRede = codigoRede;
	}
	/**
	 * @return the codigoRede
	 */
	public Integer getCodigoRede() {
		return codigoRede;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
	 * @return the listaProdutoEncontrado
	 */
	public List<ProdutoTO> getListaProdutoEncontrado() {
		return listaProdutoEncontrado;
	}
	/**
	 * @param listaProdutoEncontrado the listaProdutoEncontrado to set
	 */
	public void setListaProdutoEncontrado(List<ProdutoTO> listaProdutoEncontrado) {
		this.listaProdutoEncontrado = listaProdutoEncontrado;
	}
	/**
	 * @return the listaProdutoNaoEncontrado
	 */
	public List<ProdutoTO> getListaProdutoNaoEncontrado() {
		return listaProdutoNaoEncontrado;
	}
	/**
	 * @param listaProdutoNaoEncontrado the listaProdutoNaoEncontrado to set
	 */
	public void setListaProdutoNaoEncontrado(
			List<ProdutoTO> listaProdutoNaoEncontrado) {
		this.listaProdutoNaoEncontrado = listaProdutoNaoEncontrado;
	}
	/**
	 * @return the valorTotal
	 */
	public Double getValorTotal() {
		return valorTotal;
	}
	/**
	 * @param valorTotal the valorTotal to set
	 */
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	/**
	 * @return the valorCompras
	 */
	public Double getValorCompras() {
		return valorCompras;
	}
	/**
	 * @param valorCompras the valorCompras to set
	 */
	public void setValorCompras(Double valorCompras) {
		this.valorCompras = valorCompras;
	}
	/**
	 * @return the valorDeslocamento
	 */
	public Double getValorDeslocamento() {
		return valorDeslocamento;
	}
	/**
	 * @param valorDeslocamento the valorDeslocamento to set
	 */
	public void setValorDeslocamento(Double valorDeslocamento) {
		this.valorDeslocamento = valorDeslocamento;
	}
	
	/**
	 * @param distanciaAproximada the distanciaAproximada to set
	 */
	public void setDistanciaAproximada(BigDecimal distanciaAproximada) {
		this.distanciaAproximada = distanciaAproximada;
	}
	/**
	 * @return the distanciaAproximada
	 */
	public BigDecimal getDistanciaAproximada() {
		return distanciaAproximada;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((codigoRede == null) ? 0 : codigoRede.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MercadoTO)) {
			return false;
		}
		MercadoTO other = (MercadoTO) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		if (codigoRede == null) {
			if (other.codigoRede != null) {
				return false;
			}
		} else if (!codigoRede.equals(other.codigoRede)) {
			return false;
		}
		return true;
	}
}
