package br.iteratorsystems.cps.beans;

public class NavigationBean {
	
	/**
	 * 
	 * @return toDefaultPage
	 */
	public String toDefaultPage(){
		return "toDefaultPage";
	}
	
	/**
	 * 
	 * @return toMyCar
	 */
	public String toMyCar(){
		return "toMyCar";
	}
	
	/**
	 * 
	 * @return toFilters
	 */
	public String toFilters() {
		return "toFilters";
	}
	
	/**
	 * 
	 * @return toLoginPage
	 */
	public static String toUserAccess(){
		return "toLoginPage";
	}
	
	/**
	 * Direciona para a página de "sobre"
	 * @return toAboutPage
	 */
	public String toAboutPage(){
		return "toAboutPage";
	}
}
