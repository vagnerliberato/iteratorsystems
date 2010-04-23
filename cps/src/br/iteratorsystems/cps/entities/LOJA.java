package br.iteratorsystems.cps.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.iteratorsystems.cps.interfaces.EntityAble;

/**
 * Loja generated by hbm2java
 */
@Entity
@Table(name="loja"
    ,schema="tabelas"
)
public class LOJA  implements java.io.Serializable,EntityAble {

	 private static final long serialVersionUID = 6422147577536975375L;
	 
	 private LOJAID id;
     private REDE rede;
     private CEP cep;
     private String razaosocial;
     private String nomefantasia;
     private String cnpj;
     private String inscricaoestadual;
     private String inscricaomunicipal;
     private String nomedoresponsavelpelaloja;
     private String pais;
     private String estado;
     private String cidade;
     private String bairro;
     private String logradouro;
     private String numero;
     private String complemento;
     private Date dataultimamodificacao;
     private Set<PRODUTO> produtos = new HashSet<PRODUTO>(0);
     private Set<CONTATOLOJA> contatoLojas = new HashSet<CONTATOLOJA>(0);

    public LOJA() {
    }

	
    public LOJA(LOJAID id, REDE rede, CEP cep, String razaosocial, String nomefantasia, String cnpj, String pais, String estado, String cidade, String bairro, String logradouro, String numero, Date dataultimamodificacao) {
        this.id = id;
        this.rede = rede;
        this.cep = cep;
        this.razaosocial = razaosocial;
        this.nomefantasia = nomefantasia;
        this.cnpj = cnpj;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.dataultimamodificacao = dataultimamodificacao;
    }
    public LOJA(LOJAID id, REDE rede, CEP cep, String razaosocial, String nomefantasia, String cnpj, String inscricaoestadual, String inscricaomunicipal, String nomedoresponsavelpelaloja, String pais, String estado, String cidade, String bairro, String logradouro, String numero, String complemento, Date dataultimamodificacao, Set<PRODUTO> produtos, Set<CONTATOLOJA> contatoLojas) {
       this.id = id;
       this.rede = rede;
       this.cep = cep;
       this.razaosocial = razaosocial;
       this.nomefantasia = nomefantasia;
       this.cnpj = cnpj;
       this.inscricaoestadual = inscricaoestadual;
       this.inscricaomunicipal = inscricaomunicipal;
       this.nomedoresponsavelpelaloja = nomedoresponsavelpelaloja;
       this.pais = pais;
       this.estado = estado;
       this.cidade = cidade;
       this.bairro = bairro;
       this.logradouro = logradouro;
       this.numero = numero;
       this.complemento = complemento;
       this.dataultimamodificacao = dataultimamodificacao;
       this.produtos = produtos;
       this.contatoLojas = contatoLojas;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idLoja", column=@Column(name="id_loja", nullable=false) ), 
        @AttributeOverride(name="idRede", column=@Column(name="id_rede", nullable=false) ) } )
    public LOJAID getId() {
        return this.id;
    }
    
    public void setId(LOJAID id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_rede", nullable=false, insertable=false, updatable=false)
    public REDE getRede() {
        return this.rede;
    }
    
    public void setRede(REDE rede) {
        this.rede = rede;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cep", nullable=false)
    public CEP getCep() {
        return this.cep;
    }
    
    public void setCep(CEP cep) {
        this.cep = cep;
    }
    
    @Column(name="razaosocial", nullable=false, length=60)
    public String getRazaosocial() {
        return this.razaosocial;
    }
    
    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }
    
    @Column(name="nomefantasia", nullable=false, length=60)
    public String getNomefantasia() {
        return this.nomefantasia;
    }
    
    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }
    
    @Column(name="cnpj", nullable=false, length=20)
    public String getCnpj() {
        return this.cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    @Column(name="inscricaoestadual", length=20)
    public String getInscricaoestadual() {
        return this.inscricaoestadual;
    }
    
    public void setInscricaoestadual(String inscricaoestadual) {
        this.inscricaoestadual = inscricaoestadual;
    }
    
    @Column(name="inscricaomunicipal", length=20)
    public String getInscricaomunicipal() {
        return this.inscricaomunicipal;
    }
    
    public void setInscricaomunicipal(String inscricaomunicipal) {
        this.inscricaomunicipal = inscricaomunicipal;
    }
    
    @Column(name="nomedoresponsavelpelaloja", length=30)
    public String getNomedoresponsavelpelaloja() {
        return this.nomedoresponsavelpelaloja;
    }
    
    public void setNomedoresponsavelpelaloja(String nomedoresponsavelpelaloja) {
        this.nomedoresponsavelpelaloja = nomedoresponsavelpelaloja;
    }
    
    @Column(name="pais", nullable=false, length=50)
    public String getPais() {
        return this.pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    @Column(name="estado", nullable=false, length=2)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Column(name="cidade", nullable=false, length=50)
    public String getCidade() {
        return this.cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    @Column(name="bairro", nullable=false, length=50)
    public String getBairro() {
        return this.bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    @Column(name="logradouro", nullable=false, length=50)
    public String getLogradouro() {
        return this.logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    @Column(name="numero", nullable=false, length=6)
    public String getNumero() {
        return this.numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    @Column(name="complemento", length=20)
    public String getComplemento() {
        return this.complemento;
    }
    
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="dataultimamodificacao", nullable=false, length=13)
    public Date getDataultimamodificacao() {
        return this.dataultimamodificacao;
    }
    
    public void setDataultimamodificacao(Date dataultimamodificacao) {
        this.dataultimamodificacao = dataultimamodificacao;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="loja")
    public Set<PRODUTO> getProdutos() {
        return this.produtos;
    }
    
    public void setProdutos(Set<PRODUTO> produtos) {
        this.produtos = produtos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="loja")
    public Set<CONTATOLOJA> getContatoLojas() {
        return this.contatoLojas;
    }
    
    public void setContatoLojas(Set<CONTATOLOJA> contatoLojas) {
        this.contatoLojas = contatoLojas;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime
				* result
				+ ((dataultimamodificacao == null) ? 0 : dataultimamodificacao
						.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((inscricaoestadual == null) ? 0 : inscricaoestadual
						.hashCode());
		result = prime
				* result
				+ ((inscricaomunicipal == null) ? 0 : inscricaomunicipal
						.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime
				* result
				+ ((nomedoresponsavelpelaloja == null) ? 0
						: nomedoresponsavelpelaloja.hashCode());
		result = prime * result
				+ ((nomefantasia == null) ? 0 : nomefantasia.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result
				+ ((razaosocial == null) ? 0 : razaosocial.hashCode());
		result = prime * result + ((rede == null) ? 0 : rede.hashCode());
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
		if (!(obj instanceof LOJA)) {
			return false;
		}
		LOJA other = (LOJA) obj;
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
		if (cnpj == null) {
			if (other.cnpj != null) {
				return false;
			}
		} else if (!cnpj.equals(other.cnpj)) {
			return false;
		}
		if (complemento == null) {
			if (other.complemento != null) {
				return false;
			}
		} else if (!complemento.equals(other.complemento)) {
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (inscricaoestadual == null) {
			if (other.inscricaoestadual != null) {
				return false;
			}
		} else if (!inscricaoestadual.equals(other.inscricaoestadual)) {
			return false;
		}
		if (inscricaomunicipal == null) {
			if (other.inscricaomunicipal != null) {
				return false;
			}
		} else if (!inscricaomunicipal.equals(other.inscricaomunicipal)) {
			return false;
		}
		if (logradouro == null) {
			if (other.logradouro != null) {
				return false;
			}
		} else if (!logradouro.equals(other.logradouro)) {
			return false;
		}
		if (nomedoresponsavelpelaloja == null) {
			if (other.nomedoresponsavelpelaloja != null) {
				return false;
			}
		} else if (!nomedoresponsavelpelaloja
				.equals(other.nomedoresponsavelpelaloja)) {
			return false;
		}
		if (nomefantasia == null) {
			if (other.nomefantasia != null) {
				return false;
			}
		} else if (!nomefantasia.equals(other.nomefantasia)) {
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
		if (razaosocial == null) {
			if (other.razaosocial != null) {
				return false;
			}
		} else if (!razaosocial.equals(other.razaosocial)) {
			return false;
		}
		if (rede == null) {
			if (other.rede != null) {
				return false;
			}
		} else if (!rede.equals(other.rede)) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LOJA [bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade
				+ ", cnpj=" + cnpj + ", complemento=" + complemento
				+ ", dataultimamodificacao=" + dataultimamodificacao
				+ ", estado=" + estado + ", id=" + id + ", inscricaoestadual="
				+ inscricaoestadual + ", inscricaomunicipal="
				+ inscricaomunicipal + ", logradouro=" + logradouro
				+ ", nomedoresponsavelpelaloja=" + nomedoresponsavelpelaloja
				+ ", nomefantasia=" + nomefantasia + ", numero=" + numero
				+ ", pais=" + pais + ", razaosocial=" + razaosocial + ", rede="
				+ rede + "]";
	}
}