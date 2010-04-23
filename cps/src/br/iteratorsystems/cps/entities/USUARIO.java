package br.iteratorsystems.cps.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.iteratorsystems.cps.interfaces.EntityAble;


@Entity
@Table(name="usuario",schema="tabelas", uniqueConstraints = {@UniqueConstraint(columnNames="cpf_usuario"), @UniqueConstraint(columnNames="email")})
@SequenceGenerator(name="usuarioGenerator",sequenceName="usuario_id_usuario_seq",allocationSize=1)
public class USUARIO implements java.io.Serializable, EntityAble {

	 private static final long serialVersionUID = -6286759417748518151L;
	 
	 private Integer idUsuario;
     private String nomeUsuario;
     private String sobrenomeUsuario;
     private String cpfUsuario;
     private String rgUsuario;
     private String orgaoEspedidorUsu;
     private String dddCel;
     private String telCel;
     private String dddRes;
     private String telRes;
     private String email;
     private Date dataultimamodificacao;
     private Set<LISTAPRODUTO> listaProdutos = new HashSet<LISTAPRODUTO>(0);
     private Set<LOGIN> logins = new HashSet<LOGIN>(0);
     private Set<ENDERECO> enderecos = new HashSet<ENDERECO>(0);

    public USUARIO() {
    }
	
    public USUARIO(Integer idUsuario, String nomeUsuario, String sobrenomeUsuario, String cpfUsuario, String rgUsuario, String email, Date dataultimamodificacao) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.sobrenomeUsuario = sobrenomeUsuario;
        this.cpfUsuario = cpfUsuario;
        this.rgUsuario = rgUsuario;
        this.email = email;
        this.dataultimamodificacao = dataultimamodificacao;
    }
    public USUARIO(Integer idUsuario, String nomeUsuario, String sobrenomeUsuario, String cpfUsuario, String rgUsuario, String orgaoEspedidorUsu, String dddCel, String telCel, String dddRes, String telRes, String email, Date dataultimamodificacao, Set<LISTAPRODUTO> listaProdutos, Set<LOGIN> logins, Set<ENDERECO> enderecos) {
       this.idUsuario = idUsuario;
       this.nomeUsuario = nomeUsuario;
       this.sobrenomeUsuario = sobrenomeUsuario;
       this.cpfUsuario = cpfUsuario;
       this.rgUsuario = rgUsuario;
       this.orgaoEspedidorUsu = orgaoEspedidorUsu;
       this.dddCel = dddCel;
       this.telCel = telCel;
       this.dddRes = dddRes;
       this.telRes = telRes;
       this.email = email;
       this.dataultimamodificacao = dataultimamodificacao;
       this.listaProdutos = listaProdutos;
       this.logins = logins;
       this.enderecos = enderecos;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator="usuarioGenerator")
    @Column(name="id_usuario", unique=true, nullable=false)
    public Integer getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    @Column(name="nome_usuario", nullable=false, length=20)
    public String getNomeUsuario() {
        return this.nomeUsuario;
    }
    
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    
    @Column(name="sobrenome_usuario", nullable=false, length=30)
    public String getSobrenomeUsuario() {
        return this.sobrenomeUsuario;
    }
    
    public void setSobrenomeUsuario(String sobrenomeUsuario) {
        this.sobrenomeUsuario = sobrenomeUsuario;
    }
    
    @Column(name="cpf_usuario", unique=true, nullable=false, length=11)
    public String getCpfUsuario() {
        return this.cpfUsuario;
    }
    
    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }
    
    @Column(name="rg_usuario", nullable=false, length=15)
    public String getRgUsuario() {
        return this.rgUsuario;
    }
    
    public void setRgUsuario(String rgUsuario) {
        this.rgUsuario = rgUsuario;
    }
    
    @Column(name="orgao_espedidor_usu", length=3)
    public String getOrgaoEspedidorUsu() {
        return this.orgaoEspedidorUsu;
    }
    
    public void setOrgaoEspedidorUsu(String orgaoEspedidorUsu) {
        this.orgaoEspedidorUsu = orgaoEspedidorUsu;
    }
    
    @Column(name="ddd_cel", length=3)
    public String getDddCel() {
        return this.dddCel;
    }
    
    public void setDddCel(String dddCel) {
        this.dddCel = dddCel;
    }
    
    @Column(name="tel_cel", length=8)
    public String getTelCel() {
        return this.telCel;
    }
    
    public void setTelCel(String telCel) {
        this.telCel = telCel;
    }
    
    @Column(name="ddd_res", length=3)
    public String getDddRes() {
        return this.dddRes;
    }
    
    public void setDddRes(String dddRes) {
        this.dddRes = dddRes;
    }
    
    @Column(name="tel_res", length=8)
    public String getTelRes() {
        return this.telRes;
    }
    
    public void setTelRes(String telRes) {
        this.telRes = telRes;
    }
    
    @Column(name="email", unique=true, nullable=false, length=30)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="dataultimamodificacao", nullable=false, length=13)
    public Date getDataultimamodificacao() {
        return this.dataultimamodificacao;
    }
    
    public void setDataultimamodificacao(Date dataultimamodificacao) {
        this.dataultimamodificacao = dataultimamodificacao;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="usuario")
    public Set<LISTAPRODUTO> getListaProdutos() {
        return this.listaProdutos;
    }
    
    public void setListaProdutos(Set<LISTAPRODUTO> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="usuario")
    public Set<LOGIN> getLogins() {
        return this.logins;
    }
    
    public void setLogins(Set<LOGIN> logins) {
        this.logins = logins;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="usuario")
    public Set<ENDERECO> getEnderecos() {
        return this.enderecos;
    }
    
    public void setEnderecos(Set<ENDERECO> enderecos) {
        this.enderecos = enderecos;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cpfUsuario == null) ? 0 : cpfUsuario.hashCode());
		result = prime
				* result
				+ ((dataultimamodificacao == null) ? 0 : dataultimamodificacao
						.hashCode());
		result = prime * result + ((dddCel == null) ? 0 : dddCel.hashCode());
		result = prime * result + ((dddRes == null) ? 0 : dddRes.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result
				+ ((nomeUsuario == null) ? 0 : nomeUsuario.hashCode());
		result = prime
				* result
				+ ((orgaoEspedidorUsu == null) ? 0 : orgaoEspedidorUsu
						.hashCode());
		result = prime * result
				+ ((rgUsuario == null) ? 0 : rgUsuario.hashCode());
		result = prime
				* result
				+ ((sobrenomeUsuario == null) ? 0 : sobrenomeUsuario.hashCode());
		result = prime * result + ((telCel == null) ? 0 : telCel.hashCode());
		result = prime * result + ((telRes == null) ? 0 : telRes.hashCode());
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
		if (!(obj instanceof USUARIO)) {
			return false;
		}
		USUARIO other = (USUARIO) obj;
		if (cpfUsuario == null) {
			if (other.cpfUsuario != null) {
				return false;
			}
		} else if (!cpfUsuario.equals(other.cpfUsuario)) {
			return false;
		}
		if (dataultimamodificacao == null) {
			if (other.dataultimamodificacao != null) {
				return false;
			}
		} else if (!dataultimamodificacao.equals(other.dataultimamodificacao)) {
			return false;
		}
		if (dddCel == null) {
			if (other.dddCel != null) {
				return false;
			}
		} else if (!dddCel.equals(other.dddCel)) {
			return false;
		}
		if (dddRes == null) {
			if (other.dddRes != null) {
				return false;
			}
		} else if (!dddRes.equals(other.dddRes)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (idUsuario == null) {
			if (other.idUsuario != null) {
				return false;
			}
		} else if (!idUsuario.equals(other.idUsuario)) {
			return false;
		}
		if (nomeUsuario == null) {
			if (other.nomeUsuario != null) {
				return false;
			}
		} else if (!nomeUsuario.equals(other.nomeUsuario)) {
			return false;
		}
		if (orgaoEspedidorUsu == null) {
			if (other.orgaoEspedidorUsu != null) {
				return false;
			}
		} else if (!orgaoEspedidorUsu.equals(other.orgaoEspedidorUsu)) {
			return false;
		}
		if (rgUsuario == null) {
			if (other.rgUsuario != null) {
				return false;
			}
		} else if (!rgUsuario.equals(other.rgUsuario)) {
			return false;
		}
		if (sobrenomeUsuario == null) {
			if (other.sobrenomeUsuario != null) {
				return false;
			}
		} else if (!sobrenomeUsuario.equals(other.sobrenomeUsuario)) {
			return false;
		}
		if (telCel == null) {
			if (other.telCel != null) {
				return false;
			}
		} else if (!telCel.equals(other.telCel)) {
			return false;
		}
		if (telRes == null) {
			if (other.telRes != null) {
				return false;
			}
		} else if (!telRes.equals(other.telRes)) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "USUARIO [cpfUsuario=" + cpfUsuario + ", dataultimamodificacao="
				+ dataultimamodificacao + ", dddCel=" + dddCel + ", dddRes="
				+ dddRes + ", email=" + email + ", idUsuario=" + idUsuario
				+ ", nomeUsuario=" + nomeUsuario + ", orgaoEspedidorUsu="
				+ orgaoEspedidorUsu + ", rgUsuario=" + rgUsuario
				+ ", sobrenomeUsuario=" + sobrenomeUsuario + ", telCel="
				+ telCel + ", telRes=" + telRes + "]";
	}
}