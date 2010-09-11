package br.iteratorsystems.cps.exceptions;

/**
 * Classe de exceção de comparação
 * @author André
 *
 */
public class CpsComparacaoException extends CpsExceptions{

	/**
	 * serial
	 */
	private static final long serialVersionUID = -1524238863327061930L;
	
	/**
	 * Construtor full
	 * @param message - message
	 * @param cause - cause
	 */
	public CpsComparacaoException(String message,Throwable cause) {
		super(message,cause);
	}
	
	/**
	 * Construtor cause
	 * @param cause - cause
	 */
	public CpsComparacaoException(Throwable cause){
		super(cause);
	}
	
	/**
	 * Construtor message
	 * @param message - message
	 */
	public CpsComparacaoException(String message) {
		super(message);
	}
}
