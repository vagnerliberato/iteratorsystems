package br.iteratorsystems.cps.beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class TesteConverter implements Converter{
	
	public final static String CONVERTER_ID = "br.iteratorsystems.cps.beans.AdministrationBean";

	public Teste getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Teste t = null;
		try{
			
			if(arg2 == null) {
				return null;
			}
			//Integer.parseInt(arg2);
			t = new Teste();
			t.setNome(arg2);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		if(arg2 == null) {
			return null;
		}
		
		if(arg2 instanceof Teste) {
			return ((Teste) arg2).getNome();
		}
		
		return arg2.toString();
	}
}
