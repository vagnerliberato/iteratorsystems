package br.iteratorsystems.cps.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.iteratorsystems.cps.interfaces.EntityAble;

@Entity
@Table(name = "parametrizacao_cps", schema = "tabelas")

public class Parametrizacao implements java.io.Serializable, EntityAble {

	private static final long serialVersionUID = -940713223590186434L;
	private Integer idParametrizacao;
	private String numMaxItensLista;
	private String numMaxLojasComparacao;
	private String diretorioProcessamentoXml;
	private String diretorioImagensProCps;
	private Double valormediocombustivel;
    private Double rendimentomediocombustivel;


	public Parametrizacao() {
	}

	public Parametrizacao(Integer idParametrizacao,
			String numMaxItensLista, String numMaxLojasComparacao,
			String diretorioProcessamentoXml, String diretorioImagensProCps, Double valormediocombustivel, Double rendimentomediocombustivel) {
		this.idParametrizacao = idParametrizacao;
		this.numMaxItensLista = numMaxItensLista;
		this.numMaxLojasComparacao = numMaxLojasComparacao;
		this.diretorioProcessamentoXml = diretorioProcessamentoXml;
		this.diretorioImagensProCps = diretorioImagensProCps;
		this.valormediocombustivel = valormediocombustivel;
	    this.rendimentomediocombustivel = rendimentomediocombustivel;
	}

	@Id
	@Column(name = "id_parametrizacao", unique = true, nullable = false)
	public int getIdParametrizacao() {
		return this.idParametrizacao;
	}

	public void setIdParametrizacao(Integer idParametrizacao) {
		this.idParametrizacao = idParametrizacao;
	}

	@Column(name = "num_max_itens_lista", nullable = false, length = 5)
	public String getNumMaxItensLista() {
		return this.numMaxItensLista;
	}

	public void setNumMaxItensLista(String numMaxItensLista) {
		this.numMaxItensLista = numMaxItensLista;
	}

	@Column(name = "num_max_lojas_comparacao", nullable = false, length = 2)
	public String getNumMaxLojasComparacao() {
		return this.numMaxLojasComparacao;
	}

	public void setNumMaxLojasComparacao(String numMaxLojasComparacao) {
		this.numMaxLojasComparacao = numMaxLojasComparacao;
	}

	@Column(name = "diretorio_processamento_xml", nullable = false, length = 100)
	public String getDiretorioProcessamentoXml() {
		return this.diretorioProcessamentoXml;
	}

	public void setDiretorioProcessamentoXml(String diretorioProcessamentoXml) {
		this.diretorioProcessamentoXml = diretorioProcessamentoXml;
	}

	@Column(name = "diretorio_imagens_pro_cps", nullable = false, length = 100)
	public String getDiretorioImagensProCps() {
		return this.diretorioImagensProCps;
	}

	public void setDiretorioImagensProCps(String diretorioImagensProCps) {
		this.diretorioImagensProCps = diretorioImagensProCps;
	}
	
	@Column(name="valormediocombustivel", precision=17, scale=17)
    public Double getValormediocombustivel() {
        return this.valormediocombustivel;
    }
    
    public void setValormediocombustivel(Double valormediocombustivel) {
        this.valormediocombustivel = valormediocombustivel;
    }
    
    @Column(name="rendimentomediocombustivel", precision=17, scale=17)
    public Double getRendimentomediocombustivel() {
        return this.rendimentomediocombustivel;
    }
    
    public void setRendimentomediocombustivel(Double rendimentomediocombustivel) {
        this.rendimentomediocombustivel = rendimentomediocombustivel;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Parametrizacao [diretorioImagensProCps="
				+ diretorioImagensProCps + ", diretorioProcessamentoXml="
				+ diretorioProcessamentoXml + ", idParametrizacao="
				+ idParametrizacao + ", numMaxItensLista=" + numMaxItensLista
				+ ", numMaxLojasComparacao=" + numMaxLojasComparacao
				+ ", rendimentomediocombustivel=" + rendimentomediocombustivel
				+ ", valormediocombustivel=" + valormediocombustivel + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((diretorioImagensProCps == null) ? 0
						: diretorioImagensProCps.hashCode());
		result = prime
				* result
				+ ((diretorioProcessamentoXml == null) ? 0
						: diretorioProcessamentoXml.hashCode());
		result = prime
				* result
				+ ((idParametrizacao == null) ? 0 : idParametrizacao.hashCode());
		result = prime
				* result
				+ ((numMaxItensLista == null) ? 0 : numMaxItensLista.hashCode());
		result = prime
				* result
				+ ((numMaxLojasComparacao == null) ? 0 : numMaxLojasComparacao
						.hashCode());
		result = prime
				* result
				+ ((rendimentomediocombustivel == null) ? 0
						: rendimentomediocombustivel.hashCode());
		result = prime
				* result
				+ ((valormediocombustivel == null) ? 0 : valormediocombustivel
						.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Parametrizacao))
			return false;
		Parametrizacao other = (Parametrizacao) obj;
		if (diretorioImagensProCps == null) {
			if (other.diretorioImagensProCps != null)
				return false;
		} else if (!diretorioImagensProCps.equals(other.diretorioImagensProCps))
			return false;
		if (diretorioProcessamentoXml == null) {
			if (other.diretorioProcessamentoXml != null)
				return false;
		} else if (!diretorioProcessamentoXml
				.equals(other.diretorioProcessamentoXml))
			return false;
		if (idParametrizacao == null) {
			if (other.idParametrizacao != null)
				return false;
		} else if (!idParametrizacao.equals(other.idParametrizacao))
			return false;
		if (numMaxItensLista == null) {
			if (other.numMaxItensLista != null)
				return false;
		} else if (!numMaxItensLista.equals(other.numMaxItensLista))
			return false;
		if (numMaxLojasComparacao == null) {
			if (other.numMaxLojasComparacao != null)
				return false;
		} else if (!numMaxLojasComparacao.equals(other.numMaxLojasComparacao))
			return false;
		if (rendimentomediocombustivel == null) {
			if (other.rendimentomediocombustivel != null)
				return false;
		} else if (!rendimentomediocombustivel
				.equals(other.rendimentomediocombustivel))
			return false;
		if (valormediocombustivel == null) {
			if (other.valormediocombustivel != null)
				return false;
		} else if (!valormediocombustivel.equals(other.valormediocombustivel))
			return false;
		return true;
	}
    
}
