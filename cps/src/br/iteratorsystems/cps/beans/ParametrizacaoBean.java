package br.iteratorsystems.cps.beans;

import br.iteratorsystems.cps.entities.Tabelas_Parametrizacao;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.service.ParametrizacaoService;

public class ParametrizacaoBean {

	private Integer quantidadeMaximaDeItens;
	private Integer quantidadeMaximaDeLojas;
	private String diretorioPadraoXML;
	private String diretorioPadraoDeImagens;
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
		ParametrizacaoService parametrizacaoService = new ParametrizacaoService();
		
		if(isParametrizacaoValida()){
			Tabelas_Parametrizacao parametrizacao = new Tabelas_Parametrizacao();
			parametrizacao.setDiretorioImagensProCps(diretorioPadraoDeImagens);
			parametrizacao.setDiretorioProcessamentoXml(diretorioPadraoXML);
			parametrizacao.setNumMaxItensLista(String.valueOf(quantidadeMaximaDeItens));
			parametrizacao.setNumMaxLojasComparacao(String.valueOf(quantidadeMaximaDeLojas));
			
			parametrizacaoService.salvar(parametrizacao);
		}
	}
	private boolean isParametrizacaoValida() {
		boolean isParametrizacaoValida = true;
		String mensagemDeErro = "";
		
		this.diretorioPadraoDeImagens = this.diretorioPadraoDeImagens.replace("\\", "\\\\");
		boolean regexDiretorioPadraoDeImagensEhInvalido = !this.diretorioPadraoDeImagens.matches("[a-zA-Z]{1}[:]{1}[\\\\][0-9a-zA-Z.\\_]*");
		if(regexDiretorioPadraoDeImagensEhInvalido && this.diretorioPadraoDeImagens == null){
			isParametrizacaoValida = false;
			mensagemDeErro += "Informe um endereço de diretorio valido para salvar as imagens (Ex: c:\\teste)";
		}
		
		boolean regexDiretorioPadraoXMLEhInvalido = !this.diretorioPadraoXML.matches("[a-zA-Z]{1}[:]{1}[\\\\][0-9a-zA-Z.\\_]*");
		if(regexDiretorioPadraoXMLEhInvalido && this.diretorioPadraoXML == null){
			isParametrizacaoValida = false;
			mensagemDeErro += "\nInforme um endereço de diretorio valido para o XML (Ex: c:\\teste)";
		}
		
		boolean regexQuantidadeMaximaDeItensEhInvalida = !String.valueOf(this.quantidadeMaximaDeItens).matches("[0-9]{3}");
		if(regexQuantidadeMaximaDeItensEhInvalida && this.quantidadeMaximaDeItens > 250){
			isParametrizacaoValida = false;
			mensagemDeErro += "\nInforme uma quantidade maximo até 250 itens";
		}
		
		boolean regexQuantidadeMaximaDeLojasEhInvalida = !String.valueOf(this.quantidadeMaximaDeLojas).matches("[0-5]{1}");
		if(regexQuantidadeMaximaDeLojasEhInvalida && this.quantidadeMaximaDeLojas > 5){
			isParametrizacaoValida = false;
			mensagemDeErro += "\nInforme uma quantidade maximo até 5 lojas";
		}
		
		return isParametrizacaoValida;
	}
}
