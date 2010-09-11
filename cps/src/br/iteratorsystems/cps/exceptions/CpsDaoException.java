package br.iteratorsystems.cps.exceptions;

import java.io.Serializable;

public class CpsDaoException extends CpsExceptions implements Serializable{
	
	private static final long serialVersionUID = 062646L;
	
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
