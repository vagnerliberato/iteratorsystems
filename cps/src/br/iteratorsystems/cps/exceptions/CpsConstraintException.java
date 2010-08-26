package br.iteratorsystems.cps.exceptions;

public class CpsConstraintException extends CpsDaoException {
	
	private static final long serialVersionUID = 6666L;

	public CpsConstraintException(String message,Throwable cause) {
		super(message,cause);
	}
	public CpsConstraintException(Throwable cause){
		super(cause);
	}
	public CpsConstraintException(String message) {
		super(message);
	}
	

}
