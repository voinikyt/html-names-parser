package com.ontotext.intervirew.core.parser;

public class DocumentParserException extends RuntimeException {
	private static final long serialVersionUID = 7378531449805440236L;
		
	public DocumentParserException(String message, Exception e) {		
		super(message, e);
	}
	
}
