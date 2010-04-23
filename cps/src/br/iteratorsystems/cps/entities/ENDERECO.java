package br.iteratorsystems.cps.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.iteratorsystems.cps.interfaces.EntityAble;

@Entity
@Table(name = "endereco", schema = "tabelas")
public class ENDERECO implements java.io.Serializable, EntityAble {

	private static final long serialVersionUID = 9217830278015079708L;

	private Integer idUsuario;
	private CEP cep;
	private USUARIO usuario;
	private String pais;
	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String numero;
	private String complemeto;
	private Date dataultimamodificacao;

	public ENDERECO() {
	}

	public ENDERECO(Integer idUsuario, CEP cep, USUARIO usuario, String pais,
			String estado, String cidade, String bairro, String logradouro,
			String numero, Date dataultimamodificacao) {
		this.idUsuario = idUsuario;
		this.cep = cep;
		this.usuario = usuario;
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.dataultimamodificacao = dataultimamodificacao;
	}

	public ENDERECO(Integer idUsuario, CEP cep, USUARIO usuario, String pais,
			String estado, String cidade, String bairro, String logradouro,
			String numero, String complemeto, Date dataultimamodificacao) {
		this.idUsuario = idUsuario;
		this.cep = cep;
		this.usuario = usuario;
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemeto = complemeto;
		this.dataultimamodificacao = dataultimamodificacao;
	}

	@Id
	@Column(name = "id_usuario", unique = true, nullable = false)
	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cep", nullable = false)
	public CEP getCep() {
		return this.cep;
	}

	public void setCep(CEP cep) {
		this.cep = cep;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", unique = true, nullable = false, insertable = false, updatable = false)
	public USUARIO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(USUARIO usuario) {
		this.usuario = usuario;
	}

	@Column(name = "pais", nullable = true, length = 50)
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "estado", nullable = false, length = 2)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "cidade", nullable = false, length = 50)
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "bairro", nullable = false, length = 50)
	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name = "logradouro", nullable = false, length = 50)
	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "numero", nullable = false, length = 6)
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "complemeto", length = 20)
	public String getComplemeto() {
		return this.complemeto;
	}

	public void setComplemeto(String complemeto) {
		this.complemeto = complemeto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dataultimamodificacao", nullable = false, length = 13)
	public Date getDataultimamodificacao() {
		return this.dataultimamodificacao;
	}

	public void setDataultimamodificacao(Date dataultimamodificacao) {
		this.dataultimamodificacao = dataultimamodificacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result
				+ ((complemeto == null) ? 0 : complemeto.hashCode());
		result = prime
				* result
				+ ((dataultimamodificacao == null) ? 0 : dataultimamodificacao
						.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (!(obj instanceof ENDERECO)) {
			return false;
		}
		ENDERECO other = (ENDERECO) obj;
		if (bairro == null) {
			if (other.bairro != null) {
				return false;
			}
		} else if (!bairro.equals(other.bairro)) {
			return false;
		}
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} else if (!cep.equals(other.cep)) {
			return false;
		}
		if (cidade == null) {
			if (other.cidade != null) {
				return false;
			}
		} else if (!cidade.equals(other.cidade)) {
			return false;
		}
		if (complemeto == null) {
			if (other.complemeto != null) {
				return false;
			}
		} else if (!complemeto.equals(other.complemeto)) {
			return false;
		}
		if (dataultimamodificacao == null) {
			if (other.dataultimamodificacao != null) {
				return false;
			}
		} else if (!dataultimamodificacao.equals(other.dataultimamodificacao)) {
			return false;
		}
		if (estado == null) {
			if (other.estado != null) {
				return false;
			}
		} else if (!estado.equals(other.estado)) {
			return false;
		}
		if (idUsuario == null) {
			if (other.idUsuario != null) {
				return false;
			}
		} else if (!idUsuario.equals(other.idUsuario)) {
			return false;
		}
		if (logradouro == null) {
			if (other.logradouro != null) {
				return false;
			}
		} else if (!logradouro.equals(other.logradouro)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (pais == null) {
			if (other.pais != null) {
				return false;
			}
		} else if (!pais.equals(other.pais)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ENDERECO [bairro=" + bairro + ", cep=" + cep + ", cidade="
				+ cidade + ", complemeto=" + complemeto
				+ ", dataultimamodificacao=" + dataultimamodificacao
				+ ", estado=" + estado + ", idUsuario=" + idUsuario
				+ ", logradouro=" + logradouro + ", numero=" + numero
				+ ", pais=" + pais + ", usuario=" + usuario + "]";
	}
}