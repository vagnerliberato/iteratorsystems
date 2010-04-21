package br.iteratorsystems.cps.exceptions;

import java.io.Serializable;

public class CpsDaoException extends CpsGeneralExceptions implements Serializable{
	
	private static final long serialVersionUID = 0666L;
	
	public CpsDaoException(String message,Throwable cause){
		super(message, cause);
	}
	public CpsDaoException(String message) {
		super(message);
	}
	public CpsDaoException(Throwable cause) {
		super(cause);
	}
}
