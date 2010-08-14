package br.iteratorsystems.cps.test;

public class testRegex {
public static void main(String[] args) {
	String teste = "C:\\_";
	boolean isValido = teste.matches("[a-zA-Z]{1}[:]{1}[\\\\][0-9a-zA-Z.\\_]*");
	
	teste += "\nInforme uma quantidade maximo até 250 itens";
	teste += "\nInforme uma quantidade maximo até 250 itens";
	teste += "\nInforme uma quantidade maximo até 250 itens";
	System.out.print(teste);
	System.out.println(isValido);
}
}
