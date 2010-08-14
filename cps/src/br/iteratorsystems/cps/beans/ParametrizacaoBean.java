package br.iteratorsystems.cps.beans;

import org.richfaces.renderkit.ModalPanelRendererBase;
import org.richfaces.renderkit.html.ModalPanelRenderer;

import br.iteratorsystems.cps.entities.Tabelas_Parametrizacao;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.service.ParametrizacaoService;

public class ParametrizacaoBean {

	private Integer idParametrizacao = 1;
	private Integer quantidadeMaximaDeItens;
	private Integer quantidadeMaximaDeLojas;
	private String diretorioPadraoXML;
	private String diretorioPadraoDeImagens;
	private String mensagemDeErro = "";
	private String nomeModalMostrar = "";
	private Tabelas_Parametrizacao tabelaParametrizacao;

	ParametrizacaoService parametrizacaoService = new ParametrizacaoService();
	
	public ParametrizacaoBean(){
		try{
			popularParametrizacaoBeanParaExibirNaPagina();
		}catch(CpsDaoException e){
			System.out.println(e.getMessage());
		}
	}
	private void popularParametrizacaoBeanParaExibirNaPagina()throws CpsDaoException {
		this.tabelaParametrizacao = parametrizacaoService.obterParametrizacao(idParametrizacao);
		this.diretorioPadraoDeImagens = tabelaParametrizacao.getDiretorioImagensProCps();
		this.diretorioPadraoXML = tabelaParametrizacao.getDiretorioProcessamentoXml();
		this.quantidadeMaximaDeItens = Integer.parseInt(tabelaParametrizacao.getNumMaxItensLista().trim());
		this.quantidadeMaximaDeLojas = Integer.parseInt(tabelaParametrizacao.getNumMaxLojasComparacao().trim());
	}
	/**
	 * @return the idParametrizacao
	 */
	public Integer getIdParametrizacao() {
		return idParametrizacao;
	}
	/**
	 * @return the quantidadeMaximaDeItens
	 */
	public int getQuantidadeMaximaDeItens() {
		return quantidadeMaximaDeItens;
	}
	/**
	 * @param quantidadeMaximaDeItens the quantidadeMaximaDeItens to set
	 */
	public void setQuantidadeMaximaDeItens(int quantidadeMaximaDeItens) {
		this.quantidadeMaximaDeItens = quantidadeMaximaDeItens;
	}
	/**
	 * @return the quantidadeMaximaDeLojas
	 */
	public int getQuantidadeMaximaDeLojas() {
		return quantidadeMaximaDeLojas;
	}
	/**
	 * @param quantidadeMaximaDeLojas the quantidadeMaximaDeLojas to set
	 */
	public void setQuantidadeMaximaDeLojas(int quantidadeMaximaDeLojas) {
		this.quantidadeMaximaDeLojas = quantidadeMaximaDeLojas;
	}
	/**
	 * @return the diretorioPadraoXML
	 */
	public String getDiretorioPadraoXML() {
		return diretorioPadraoXML;
	}
	/**
	 * @param diretorioPadraoXML the diretorioPadraoXML to set
	 */
	public void setDiretorioPadraoXML(String diretorioPadraoXML) {
		this.diretorioPadraoXML = diretorioPadraoXML;
	}
	/**
	 * @return the diretorioPadraoDeImagens
	 */
	public String getDiretorioPadraoDeImagens() {
		return diretorioPadraoDeImagens;
	}
	/**
	 * @param diretorioPadraoDeImagens the diretorioPadraoDeImagens to set
	 */
	public void setDiretorioPadraoDeImagens(String diretorioPadraoDeImagens) {
		this.diretorioPadraoDeImagens = diretorioPadraoDeImagens;
	}
	
	public void salvarInformacoesDeParametrizacao() throws CpsDaoException{
		
		if(isParametrizacaoValida()){
			tabelaParametrizacao.setIdParametrizacao(idParametrizacao);
			tabelaParametrizacao.setDiretorioImagensProCps(diretorioPadraoDeImagens);
			tabelaParametrizacao.setDiretorioProcessamentoXml(diretorioPadraoXML);
			tabelaParametrizacao.setNumMaxItensLista(String.valueOf(quantidadeMaximaDeItens));
			tabelaParametrizacao.setNumMaxLojasComparacao(String.valueOf(quantidadeMaximaDeLojas));
			
			parametrizacaoService.salvar(tabelaParametrizacao);
			this.nomeModalMostrar =  "";
		}else{
			this.nomeModalMostrar =  "Richfaces.showModalPanel('modalParametrizacaoErro')";
		}
	}
	private boolean isParametrizacaoValida() {
		boolean isParametrizacaoValida = true;
		mensagemDeErro = "";
		
		//this.diretorioPadraoDeImagens = this.diretorioPadraoDeImagens.replace("\\", "\\\\");
		boolean regexDiretorioPadraoDeImagensEhInvalido = !this.diretorioPadraoDeImagens.matches("[a-zA-Z]{1}[:]{1}[\\\\][0-9a-zA-Z.\\_]*");
		if(regexDiretorioPadraoDeImagensEhInvalido || this.diretorioPadraoDeImagens == null){
			isParametrizacaoValida = false;
			mensagemDeErro += "Endereço de imagens invalido(Ex: c:\\teste).";
		}
		
		//this.diretorioPadraoXML = this.diretorioPadraoXML.replace("\\", "\\\\");
		boolean regexDiretorioPadraoXMLEhInvalido = !this.diretorioPadraoXML.matches("[a-zA-Z]{1}[:]{1}[\\\\][0-9a-zA-Z.\\_]*");
		if(regexDiretorioPadraoXMLEhInvalido || this.diretorioPadraoXML == null){
			isParametrizacaoValida = false;
			mensagemDeErro += "\nEndereço de XML invalido(Ex: c:\\teste).";
		}
		
		boolean regexQuantidadeMaximaDeItensEhInvalida = !String.valueOf(this.quantidadeMaximaDeItens).matches("[0-9]{3}");
		if(regexQuantidadeMaximaDeItensEhInvalida || this.quantidadeMaximaDeItens > 250){
			isParametrizacaoValida = false;
			mensagemDeErro += "\nQuantidade de itens invalido (maximo 250)";
		}
		
		boolean regexQuantidadeMaximaDeLojasEhInvalida = !String.valueOf(this.quantidadeMaximaDeLojas).matches("[0-5]{1}");
		if(regexQuantidadeMaximaDeLojasEhInvalida || this.quantidadeMaximaDeLojas > 5){
			isParametrizacaoValida = false;
			mensagemDeErro += "\nQuantidade de lojas invalido (maximo 5)";
		}
		
		if(!isParametrizacaoValida){
			
		}
		
		return isParametrizacaoValida;
	}
	
	public String getMensagemDeErro() {
		return mensagemDeErro;
	}
	
	public void setMensagemDeErro(String mensagemDeErro) {
		this.mensagemDeErro = mensagemDeErro;
	}
	
	public String getNomeModalMostrar() {
		return nomeModalMostrar;
	}
	
	public void setNomeModalMostrar(String nomeModalMostrar) {
		this.nomeModalMostrar = nomeModalMostrar;
	}
}
