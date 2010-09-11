package br.iteratorsystems.cps.exceptions;

public class CpsHandlerException extends CpsExceptions {

	private static final long serialVersionUID = 11666L;
	
	public CpsHandlerException(String message,Throwable cause){
		super(message,cause);
	}
	public CpsHandlerException(String message){
		super(message);
	}
	public CpsHandlerException(Throwable cause){
		super(cause);
	}
}
