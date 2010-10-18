package br.iteratorsystems.cps.test;

import br.iteratorsystems.cps.beans.ParametrizacaoBean;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class testRegex {
public static void main(String[] args) throws CpsDaoException {
	/*String teste = "C:\\_";
	boolean isValido = teste.matches("[a-zA-Z]{1}[:]{1}[\\\\][0-9a-zA-Z.\\_]*");
	
	teste += "\nInforme uma quantidade maximo até 250 itens";
	teste += "\nInforme uma quantidade maximo até 250 itens";
	teste += "\nInforme uma quantidade maximo até 250 itens";
	System.out.print(teste);
	System.out.println(isValido);*/
	
	ParametrizacaoBean bean = new ParametrizacaoBean();
	bean.setDiretorioPadraoDeImagens("c:\\abc");
	bean.setDiretorioPadraoXML("c:\\ccccccc");
	bean.setQuantidadeMaximaDeItens(69);
	bean.setQuantidadeMaximaDeLojas(4);
	
	bean.salvarInformacoesDeParametrizacao();
}
}
