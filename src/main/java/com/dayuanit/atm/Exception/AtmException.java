package com.dayuanit.atm.Exception;

public class AtmException extends RuntimeException {
	

	private static final long serialVersionUID = 1L;

	public AtmException() {
		
	}
	
	public AtmException(String errorInfo) {
		super(errorInfo);
	}

}
