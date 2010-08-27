package br.iteratorsystems.cps.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.iteratorsystems.cps.interfaces.EntityAble;

/**
 * Cep generated by hbm2java
 */
@Entity
@Table(name = "cep", schema = "tabelas")
public class Cep implements java.io.Serializable, EntityAble {

	private static final long serialVersionUID = -7084599274868779517L;

	private String cep;
	private Localidade localidade;
	private String uf;
	private String idTipologradouro;
	private String logradouro;
	private String bairro1;
	private String bairro2;
	private String complemento;
	private Set<Endereco> enderecos = new HashSet<Endereco>(0);
	private Set<Loja> lojas = new HashSet<Loja>(0);

	public Cep() {
	}

	public Cep(String cep, Localidade localidade, String uf,
			String idTipologradouro, String logradouro, String bairro1,
			String bairro2, String complemento) {
		this.cep = cep;
		this.localidade = localidade;
		this.uf = uf;
		this.idTipologradouro = idTipologradouro;
		this.logradouro = logradouro;
		this.bairro1 = bairro1;
		this.bairro2 = bairro2;
		this.complemento = complemento;
	}

	public Cep(String cep, Localidade localidade, String uf,
			String idTipologradouro, String logradouro, String bairro1,
			String bairro2, String complemento, Set<Endereco> enderecos,
			Set<Loja> lojas) {
		this.cep = cep;
		this.localidade = localidade;
		this.uf = uf;
		this.idTipologradouro = idTipologradouro;
		this.logradouro = logradouro;
		this.bairro1 = bairro1;
		this.bairro2 = bairro2;
		this.complemento = complemento;
		this.enderecos = enderecos;
		this.lojas = lojas;
	}

	@Id
	@Column(name = "cep", unique = true, nullable = false, length = 10)
	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep.replace("-", "");
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_localidade", nullable = false)
	public Localidade getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	@Column(name = "uf", nullable = false, length = 2)
	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Column(name = "id_tipologradouro", nullable = false, length = 4)
	public String getIdTipologradouro() {
		return this.idTipologradouro;
	}

	public void setIdTipologradouro(String idTipologradouro) {
		this.idTipologradouro = idTipologradouro;
	}

	@Column(name = "logradouro", nullable = false, length = 60)
	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "bairro1", nullable = false, length = 6)
	public String getBairro1() {
		return this.bairro1;
	}

	public void setBairro1(String bairro1) {
		this.bairro1 = bairro1;
	}

	@Column(name = "bairro2", nullable = false, length = 6)
	public String getBairro2() {
		return this.bairro2;
	}

	public void setBairro2(String bairro2) {
		this.bairro2 = bairro2;
	}

	@Column(name = "complemento", nullable = false, length = 65)
	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cep")
	public Set<Endereco> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cep")
	public Set<Loja> getLojas() {
		return this.lojas;
	}

	public void setLojas(Set<Loja> lojas) {
		this.lojas = lojas;
	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((bairro1 == null) ? 0 : bairro1.hashCode());
//		result = prime * result + ((bairro2 == null) ? 0 : bairro2.hashCode());
//		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
//		result = prime * result
//				+ ((complemento == null) ? 0 : complemento.hashCode());
//		result = prime
//				* result
//				+ ((idTipologradouro == null) ? 0 : idTipologradouro.hashCode());
//		result = prime * result
//				+ ((localidade == null) ? 0 : localidade.hashCode());
//		result = prime * result
//				+ ((logradouro == null) ? 0 : logradouro.hashCode());
//		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
//		return result;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (obj == null) {
//			return false;
//		}
//		if (!(obj instanceof Cep)) {
//			return false;
//		}
//		Cep other = (Cep) obj;
//		if (bairro1 == null) {
//			if (other.bairro1 != null) {
//				return false;
//			}
//		} else if (!bairro1.equals(other.bairro1)) {
//			return false;
//		}
//		if (bairro2 == null) {
//			if (other.bairro2 != null) {
//				return false;
//			}
//		} else if (!bairro2.equals(other.bairro2)) {
//			return false;
//		}
//		if (cep == null) {
//			if (other.cep != null) {
//				return false;
//			}
//		} else if (!cep.equals(other.cep)) {
//			return false;
//		}
//		if (complemento == null) {
//			if (other.complemento != null) {
//				return false;
//			}
//		} else if (!complemento.equals(other.complemento)) {
//			return false;
//		}
//		if (idTipologradouro == null) {
//			if (other.idTipologradouro != null) {
//				return false;
//			}
//		} else if (!idTipologradouro.equals(other.idTipologradouro)) {
//			return false;
//		}
//		if (localidade == null) {
//			if (other.localidade != null) {
//				return false;
//			}
//		} else if (!localidade.equals(other.localidade)) {
//			return false;
//		}
//		if (logradouro == null) {
//			if (other.logradouro != null) {
//				return false;
//			}
//		} else if (!logradouro.equals(other.logradouro)) {
//			return false;
//		}
//		if (uf == null) {
//			if (other.uf != null) {
//				return false;
//			}
//		} else if (!uf.equals(other.uf)) {
//			return false;
//		}
//		return true;
//	}
//
	
	@Override
	public String toString() {
		return "Cep [bairro1=" + bairro1 + ", bairro2=" + bairro2 + ", cep="
				+ cep + ", complemento=" + complemento + ", idTipologradouro="
				+ idTipologradouro + ", localidade=" + localidade
				+ ", logradouro=" + logradouro + ", uf=" + uf + "]";
	}
}