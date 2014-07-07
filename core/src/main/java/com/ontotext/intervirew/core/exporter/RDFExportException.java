package com.ontotext.intervirew.core.exporter;

public class RDFExportException extends RuntimeException {
	private static final long serialVersionUID = -1028959621370855906L;

	public RDFExportException(String message, Exception root) {
		super(message, root);
	}
}
