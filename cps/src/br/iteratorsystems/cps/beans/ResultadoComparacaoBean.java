package br.iteratorsystems.cps.beans;

import java.util.List;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import br.iteratorsystems.cps.to.ResultadoComparacaoTO;

/**
 * Classe bean de resultado da comparação
 * @author André
 *
 */
public class ResultadoComparacaoBean {
	
	private List<ResultadoComparacaoTO> listaComparacao;
	private String escolhaComparacao;
	private String chaveGoogleMaps;
	private FiltersBean filtersBean;
	private String campoHidden;
	
	private static final String CHAVE_GOOGLE_MAPS = 
		"ABQIAAAA7j_Q-rshuWkc8HyFI4V2HxQYPm-xtd00hTQOC0OXpAMO40FHAxT29dNBGfxqMPq5zwdeiDSHEPL89A";
	
	/**
	 * Obtem o resultado da comparacao
	 */
	private void obterResultadoComparacao() {
		FacesContext context = FacesContext.getCurrentInstance();
		ELResolver el = context.getApplication().getELResolver();
		setFiltersBean((FiltersBean) el.getValue(context.getELContext(), null,
				"filtersBean"));
		if(this.getFiltersBean() != null) {
			setListaComparacao(this.getFiltersBean().getListaComparacao());
			obterEscolhaComparacao();
			obterChaveGoogleMaps();
		}
	}
	
	/**
	 * obtem a chave do google maps para ser usada no mapa
	 */
	private void obterChaveGoogleMaps() {
		setChaveGoogleMaps(CHAVE_GOOGLE_MAPS);
	}

	/**
	 * Obtem a escolha de comparação feita pelo usuário
	 */
	private void obterEscolhaComparacao() {
		if (filtersBean.getBuscarPeloMenorPreco() && !filtersBean.getBuscarPelaMenorDistancia()) {
			escolhaComparacao = "Busca pelo menor preço!";
		} else if (filtersBean.getBuscarPelaMenorDistancia() && !filtersBean.getBuscarPeloMenorPreco()) {
			escolhaComparacao = "Busca pela menor distância!";
		} else {
			escolhaComparacao = "Busca pelo menor preço e distância!";
		}
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
	 * @param campoHidden the campoHidden to set
	 */
	public void setCampoHidden(String campoHidden) {
		this.campoHidden = campoHidden;
	}

	/**
	 * @return the campoHidden
	 */
	public String getCampoHidden() {
		obterResultadoComparacao();
		return campoHidden;
	}

	/**
	 * @param filtersBean the filtersBean to set
	 */
	public void setFiltersBean(FiltersBean filtersBean) {
		this.filtersBean = filtersBean;
	}

	/**
	 * @return the filtersBean
	 */
	public FiltersBean getFiltersBean() {
		return filtersBean;
	}

	/**
	 * @param escolhaComparacao the escolhaComparacao to set
	 */
	public void setEscolhaComparacao(String escolhaComparacao) {
		this.escolhaComparacao = escolhaComparacao;
	}

	/**
	 * @return the escolhaComparacao
	 */
	public String getEscolhaComparacao() {
		return escolhaComparacao;
	}

	/**
	 * @param chaveGoogleMaps the chaveGoogleMaps to set
	 */
	public void setChaveGoogleMaps(String chaveGoogleMaps) {
		this.chaveGoogleMaps = chaveGoogleMaps;
	}

	/**
	 * @return the chaveGoogleMaps
	 */
	public String getChaveGoogleMaps() {
		return chaveGoogleMaps;
	}
}
